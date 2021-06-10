package com.example.vendingmachine.context;

import com.example.vendingmachine.state.VendingMachineState;

import java.util.Map;

/**
 * API methods in addition to the vending states
 */
public interface VendingMachineMachine extends VendingMachineState {
    VendingMachineState getVendingMachineState();

    void registerCoins(Map<Integer, Integer> coinsToAdd);
}
