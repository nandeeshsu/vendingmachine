package com.example.vendingmachine.context;

import com.example.vendingmachine.adapter.CoinInventory;
import com.example.vendingmachine.state.Initialised;
import com.example.vendingmachine.state.InitialisedEmpty;
import com.example.vendingmachine.state.Uninitialised;
import com.example.vendingmachine.state.VendingMachineState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Vending machine  Starts in the Uninitialised state, after first initialisation, changes state by observing
 * the coins in the inventory
 */
public class VendingMachineMachineContext implements VendingMachineMachine {

    private static final Logger logger = LoggerFactory.getLogger(VendingMachineMachineContext.class);

    private final VendingMachineState initialisedState;
    private final VendingMachineState uninitialisedState;
    private final VendingMachineState initialisedEmptyState;
    private VendingMachineState vendingMachineState;

    private final CoinInventory coinInventory;

    public VendingMachineMachineContext() {
        this.initialisedState = new Initialised(this);
        this.uninitialisedState = new Uninitialised(this);
        this.initialisedEmptyState = new InitialisedEmpty(this);

        this.vendingMachineState = uninitialisedState;

        this.coinInventory = new CoinInventory();
        this.coinInventory.addObserver(this);
    }

    @Override
    public void initialiseWithCoin(Map<Integer, Integer> coinsToAdd) {
        vendingMachineState.initialiseWithCoin(coinsToAdd);
    }

    @Override
    public void uninitialise() {
        vendingMachineState.uninitialise();
    }

    @Override
    public void initialise() {
        vendingMachineState.initialise();
    }

    @Override
    public void registerCoins(Map<Integer, Integer> coinsToAdd) {
        this.coinInventory.addCoins(coinsToAdd);
    }

    @Override
    public String getStatus() {
        return vendingMachineState.getStatus();
    }

    @Override
    public List<Integer> dispenseCoins(int value) {
        return vendingMachineState.dispenseCoins(value);
    }

    public void update() {
        if (coinInventory.getCoinStore().isEmpty()) {
            if (vendingMachineState == initialisedState) {
                vendingMachineState = initialisedEmptyState;
                logger.info("Switching to {}", vendingMachineState.getStatus());
            }
        } else if (vendingMachineState == initialisedEmptyState) {
            vendingMachineState = initialisedState;
            logger.info("Switching to {}", vendingMachineState.getStatus());
        }
    }

    public VendingMachineState getVendingMachineState() {
        return vendingMachineState;
    }

    public void setVendingMachineState(VendingMachineState vendingMachineState) {
        this.vendingMachineState = vendingMachineState;
    }

    public VendingMachineState getInitialisedState() {
        return initialisedState;
    }

    public VendingMachineState getUninitialisedState() {
        return uninitialisedState;
    }

    public VendingMachineState getInitialisedEmptyState() {
        return initialisedEmptyState;
    }

    public CoinInventory getCoinInventory() {
        return coinInventory;
    }
}
