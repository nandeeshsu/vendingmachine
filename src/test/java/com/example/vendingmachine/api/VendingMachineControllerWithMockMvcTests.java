package com.example.vendingmachine.api;

import com.example.vendingmachine.model.Coin;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
 * In this test, the full Spring application context is started but without the the cost of server.
 */

@SpringBootTest
@AutoConfigureMockMvc
class VendingMachineControllerWithMockMvcTests {

    @Autowired
    private MockMvc mockMvc;

    private static String asJsonString(final Object obj) throws Exception {
        return new ObjectMapper().writeValueAsString(obj);
    }

    @Test
    void testInitializeVendingMachine() throws Exception {
        List<Coin> coins = new ArrayList<>();
        Coin coin = new Coin();
        coin.setDenomination(20);
        coin.setQuantity(1);
        coins.add(coin);

        this.mockMvc.perform(post("/public/v1/initialize")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .content(asJsonString(coins)))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
