package com.example.vendingmachine.api;

import com.example.vendingmachine.model.Coin;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
 * in this test, Spring Boot instantiates only the web layer rather than the whole context. This should be way to write the Junit
 */
@WebMvcTest
class WebLayerTest {

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
