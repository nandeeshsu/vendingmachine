package com.example.vendingmachine.state;

import com.example.vendingmachine.context.VendingMachineMachineContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class Initialised implements VendingMachineState {

    public static final  String STATUS = "INITIALISED";
    private static final Logger logger = LoggerFactory.getLogger(Initialised.class);
    private final VendingMachineMachineContext vendingMachine;

    public Initialised(VendingMachineMachineContext vendingMachine) {
        this.vendingMachine = vendingMachine;
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
    public List<Integer> dispenseCoins(int value) {
        return vendingMachine.getCoinInventory().removeCoins(value);
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
