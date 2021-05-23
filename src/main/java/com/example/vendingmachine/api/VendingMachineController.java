package com.example.vendingmachine.api;

import com.example.vendingmachine.context.VendingMachineMachineContext;
import com.example.vendingmachine.exception.ServiceErrorCode;
import com.example.vendingmachine.exception.VendingMachingException;
import com.example.vendingmachine.model.Coin;
import com.example.vendingmachine.model.VendingMachineStatus;
import com.example.vendingmachine.state.Initialised;
import com.example.vendingmachine.state.InitialisedEmpty;
import com.example.vendingmachine.state.Uninitialised;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

/*
 * REST API: A software component that tracks change (as in coins) within the vending machines.
 */
@RestController
@Slf4j
@RequestMapping("public/v1")
@Validated
public class VendingMachineController {

    //This could be loaded as spring managed bean
    //Sets the initial vending machine state to Uninitialised, created empty CoinInventory and adds Observer to track the state.
    private final VendingMachineMachineContext vendingMachine = new VendingMachineMachineContext();

    /*
     * Initialise the vending machine to a known state, for use when the machine is set up.
     * This should include setting the initial float (the coins placed in the machine for customer change) which should be accepted as a parameter
     */
    @PostMapping(value = "/initialize", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public VendingMachineStatus initialiseVendingMachine(@Valid @RequestBody(required = true) List<Coin> coins){
        var vendingMachineStatus = new VendingMachineStatus();

        //Initialise the coin if the state is Uninitialised
        if (!vendingMachine.getStatus().equals(Initialised.STATUS) && !vendingMachine.getStatus().equals(InitialisedEmpty.STATUS)) {
            vendingMachine.initialiseWithCoin(coins.stream().collect(Collectors.toMap(Coin::getDenomination, Coin::getQuantity, Integer::sum)));
        }

        //This is added just to show CoinInventory
        vendingMachineStatus.setCoinStore(vendingMachine.getCoinInventory().getCoinStore());
        vendingMachineStatus.setStatus(vendingMachine.getStatus());

        if (vendingMachine.getStatus().equals(Uninitialised.STATUS)) {
            throw new VendingMachingException("Internal server error: Cloud not initialise the vending machine", ServiceErrorCode.INTERNAL_SERVICE_ERROR);
        }
        return vendingMachineStatus;
    }

    /*
     * Register coins that have been deposited by a user
     */
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public VendingMachineStatus registerCoinsDepositedByAUser(@RequestBody(required = true) @NonNull List<Coin> coins){
        if (vendingMachine.getStatus().equals(Uninitialised.STATUS)){
            throw new VendingMachingException("Cannot register, initialize the vending machine before registering", ServiceErrorCode.BAD_REQUEST);
        }
        vendingMachine.registerCoins(coins.stream().collect(Collectors.toMap(Coin::getDenomination, Coin::getQuantity, Integer::sum)));
        var vendingMachineStatus = new VendingMachineStatus();
        vendingMachineStatus.setStatus(vendingMachine.getStatus());
        vendingMachineStatus.setCoinStore(vendingMachine.getCoinInventory().getCoinStore());
        return vendingMachineStatus;
    }

    /*
     * Produce a collection of coins that sum to a particular value (accepted as a parameter) from the coins available in the machine (for the purpose of returning change to the user),
     * and remove the coins from the machine
     */
    @GetMapping("/dispense")
    public List<Integer> dispenseCoin(@RequestParam(required = true) @Min(1) @Max(Integer.MAX_VALUE) Integer amountToDispense){
        if (vendingMachine.getStatus().equals(Uninitialised.STATUS) || vendingMachine.getCoinInventory().getCoinStore().isEmpty()){
            throw new VendingMachingException("No coins to dispense", ServiceErrorCode.BAD_REQUEST);
        }

        List<Integer> dispensedCoins = vendingMachine.dispenseCoins(amountToDispense);

        if(dispensedCoins.isEmpty()){
            throw new VendingMachingException("Cannot dispense coin, try different value", ServiceErrorCode.BAD_REQUEST);
        }
        return dispensedCoins;
    }
}
