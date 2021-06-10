package com.example.vendingmachine.state;

import com.example.vendingmachine.context.VendingMachineMachineContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Uninitialised implements VendingMachineState {

    public static final String STATUS = "UNINITIALISED";
    public static final String STATE = " state";
    private static final Logger logger = LoggerFactory.getLogger(Uninitialised.class);
    private final VendingMachineMachineContext vendingMachine;

    public Uninitialised(VendingMachineMachineContext vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    @Override
    public void initialise() {
        if (vendingMachine.getCoinInventory().getCoinStore().isEmpty()) {
            vendingMachine.setVendingMachineState(vendingMachine.getInitialisedEmptyState());
        } else {
            vendingMachine.setVendingMachineState(vendingMachine.getInitialisedState());
        }
        logger.info("Switching to {}", vendingMachine.getStatus() + STATE);
    }

    @Override
    public void initialiseWithCoin(Map<Integer, Integer> coinsToAdd) {
        this.initialise();
        vendingMachine.registerCoins(coinsToAdd);
    }

    @Override
    public List<Integer> dispenseCoins(int value) {
        logger.info("Cannot dispense coins in " + Initialised.STATUS + STATE);
        return Collections.emptyList();
    }

    @Override
    public void uninitialise() {
        logger.info("Already in " + STATUS + STATE);
    }

    @Override
    public String getStatus() {
        return STATUS;
    }
}
