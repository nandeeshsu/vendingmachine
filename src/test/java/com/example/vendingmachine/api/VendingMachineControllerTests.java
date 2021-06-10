package com.example.vendingmachine.api;

import com.example.vendingmachine.exception.VendingMachingException;
import com.example.vendingmachine.model.Coin;
import com.example.vendingmachine.model.VendingMachineStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
/*
 * This would be like Integration Test
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VendingMachineControllerTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testInitializeVendingMachine() {
        List<Coin> coins = new ArrayList<>();
        Coin coin = new Coin();
        coin.setDenomination(20);
        coin.setQuantity(1);
        coins.add(coin);

        VendingMachineStatus vendingMachineStatus = this.restTemplate.postForObject("http://localhost:" + port + "/vendingmachine/public/v1/initialize", coins, VendingMachineStatus.class);

        assertThat(vendingMachineStatus).isNotNull();
        assertThat(vendingMachineStatus.getStatus()).isEqualTo("INITIALISED");
        assertThat(vendingMachineStatus.getCoinStore()).isNotNull();
        assertThat(vendingMachineStatus.getCoinStore()).containsEntry(20, 1);
    }

    @Test
    void testRegisterCoinsForVendingMachine() {
        List<Coin> coins = new ArrayList<>();
        Coin coin = new Coin();
        coin.setDenomination(20);
        coin.setQuantity(1);
        coins.add(coin);

        VendingMachineStatus vendingMachineStatus = this.restTemplate.postForObject("http://localhost:" + port + "/vendingmachine/public/v1/register", coins, VendingMachineStatus.class);

        assertThat(vendingMachineStatus).isNotNull();
        assertThat(vendingMachineStatus.getStatus()).isEqualTo("INITIALISED");
        assertThat(vendingMachineStatus.getCoinStore()).isNotNull();
        assertThat(vendingMachineStatus.getCoinStore()).containsEntry(20, 2);
    }

    @Test
    void testDispenseCoinsForVendingMachine() {
        VendingMachingException vendingMachingException = this.restTemplate.getForObject("http://localhost:" + port + "/vendingmachine/public/v1/dispense?amountToDispense=30", VendingMachingException.class);
        assertThat(vendingMachingException.getMessage()).isEqualTo("Cannot dispense coin, try different value");
    }
}
