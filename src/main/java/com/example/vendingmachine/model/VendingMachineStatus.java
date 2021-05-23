package com.example.vendingmachine.model;

import lombok.Data;

import java.util.Map;

/*
 * Represent the state of the vending machine
 */
@Data
public class VendingMachineStatus {
    String status;
    Map<Integer, Integer> coinStore;
}
