package com.example.vendingmachine.coin;

import org.hamcrest.collection.IsEmptyCollection;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CoinInventoryTests {

    @Test
    void unableToProvideChange() {

        Map<Integer, Integer> coinStore = new TreeMap<>();
        coinStore.put(21, 10);
        coinStore.put(2, 5);

        CoinInventory coin = new CoinInventory(coinStore);
        List<Integer> change = coin.removeCoins(26);
        assertEquals(0, change.size());
        assertThat(change, IsEmptyCollection.empty());
    }

    @Test
    void forceRecursion() {

        Map<Integer, Integer> coinStore = new TreeMap<>();
        coinStore.put(20, 1);
        coinStore.put(10, 2);
        coinStore.put(5, 2);
        coinStore.put(2, 3);

        CoinInventory coin = new CoinInventory(coinStore);
        List<Integer> change = coin.removeCoins(21);
        assertThat(change, containsInAnyOrder(2, 2, 2, 5, 10));
    }

    @Test
    void returnsSameChangeAfterRemovalAndTopUp() {

        Map<Integer, Integer> coinStore = new TreeMap<>();
        coinStore.put(1, 10);
        coinStore.put(2, 2);
        coinStore.put(5, 10);
        coinStore.put(10, 5);
        coinStore.put(20, 5);
        coinStore.put(50, 5);
        coinStore.put(100, 5);
        coinStore.put(200, 5);

        CoinInventory coin = new CoinInventory(coinStore);

        List<Integer> change = coin.removeCoins(99);
        assertThat(change, containsInAnyOrder(2, 2, 5, 20, 20, 50));

        //No twos remaining
        change = coin.removeCoins(99);
        assertThat(change, containsInAnyOrder(1, 1, 1, 1, 5, 20, 20, 50));

        //Top up the twos and twenties
        TreeMap<Integer, Integer> cf2 = new TreeMap<>();
        cf2.put(2, 2);
        cf2.put(20, 1);
        coin.addCoins(cf2);

        change = coin.removeCoins(99);
        assertThat(change, containsInAnyOrder(2, 2, 5, 20, 20, 50));
    }

}
