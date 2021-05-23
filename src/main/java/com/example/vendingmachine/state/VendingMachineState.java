package com.example.vendingmachine.state;

import java.util.List;
import java.util.Map;

/**
 * Vending machine state methods
 */
public interface VendingMachineState {
    String getStatus();
    List<Integer> dispenseCoins(int value);
    void uninitialise();
    void initialise();
    void initialiseWithCoin(Map<Integer, Integer> coinsToAdd);
}
