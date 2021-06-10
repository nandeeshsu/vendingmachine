package com.example.vendingmachine.model;

public enum Denomination {
    ONE_PENCE(1),
    TWO_PENCE(2),
    FIVE_PENCE(5),
    TEN_PENCE(10),
    TWENTY_PENCE(20),
    FIFTY_PENCE(50),
    ONE_POUND(1),
    TWO_POUND(2),
    ;

    private final Integer value;

    Denomination(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
