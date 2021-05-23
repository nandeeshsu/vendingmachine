package com.example.vendingmachine.model;

import lombok.Data;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/*
 * Represent the Coin denomination and quantity
 */
@Data
public class Coin {

    @Min(value = 1, message = "Minimum denomination should be 1")
    @Max(value = Integer.MAX_VALUE, message = "Maximum denomination should be Integer.MAX_VALUE")
    Integer denomination;

    @Min(value = 1, message = "Minimum quantity should be 1")
    @Max(value = Integer.MAX_VALUE, message = "Maximum quantity should be Integer.MAX_VALUE")
    Integer quantity;
}
