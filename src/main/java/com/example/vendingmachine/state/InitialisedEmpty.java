package com.example.vendingmachine.state;

import com.example.vendingmachine.context.VendingMachineMachineContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InitialisedEmpty implements VendingMachineState {

    public static final String STATUS = "INITIALISED-EMPTY";
    private static final Logger logger = LoggerFactory.getLogger(InitialisedEmpty.class);
    private final VendingMachineMachineContext vendingMachine;

    public InitialisedEmpty(VendingMachineMachineContext vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    @Override
    public List<Integer> dispenseCoins(int value) {
        return new ArrayList<>();
    }


    @Override
    public void initialise() {
        logger.info("Already in " + STATUS + "state");
    }

    @Override
    public void initialiseWithCoin(Map<Integer, Integer> coinsToAdd) {
        this.initialise();
    }

    @Override
    public void uninitialise() {
        logger.info("Switching to " + Uninitialised.STATUS + " state");
        vendingMachine.setVendingMachineState(vendingMachine.getUninitialisedState());
    }

    @Override
    public String getStatus() {
        return STATUS;
    }

}
