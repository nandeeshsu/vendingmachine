package com.example.vendingmachine.adapter;

import com.example.vendingmachine.context.VendingMachineMachineContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * COIN Inventory: Stores the coin denomination, and the number of coins available.
 */
public class CoinInventory {

    private static final Logger logger = LoggerFactory.getLogger(CoinInventory.class);

    private final List<VendingMachineMachineContext> vendingMachines = new ArrayList<>();

    //CoinInventory: Stores the coin denomination, and the number of coin for each of the denomination
    private final TreeMap<Integer, Integer> coinStore;

    /**
     * Creates a new CoinInventory
     */
    public CoinInventory() {
        this.coinStore = new TreeMap<>(Collections.reverseOrder());
        broadcastStateChange();
    }

    /**
     * @param coinStore Creates a new CoinInventory. Copies the contents of the map into a new map
     *                  storing the keys in reverse natural order
     */
    public CoinInventory(Map<Integer, Integer> coinStore) {
        this.coinStore = new TreeMap<>(Collections.reverseOrder());
        addCoins(coinStore);
        broadcastStateChange();
    }


    /**
     * Issues a copy of the Coin Inventory map
     *
     * @return
     */
    public Map<Integer, Integer> getCoinStore() {
        Map<Integer, Integer> coinInventoryCopy = new TreeMap<>(Collections.reverseOrder());
        coinInventoryCopy.putAll(coinStore);
        return coinInventoryCopy;
    }

    /**
     * Adds more coins to the CoinInventory
     *
     * @param coinToAdd
     */
    public void addCoins(Map<Integer, Integer> coinToAdd) {
        boolean state = coinStore.isEmpty();
        coinToAdd.forEach((k, v) -> {
            if (k != null && v != null && k > 0 && v > 0) {
                Integer currentValue = coinStore.get(k);
                if (currentValue == null) {
                    currentValue = 0;
                }
                coinStore.put(k, v + currentValue);
            }
        });
        if (state != coinStore.isEmpty())
            broadcastStateChange();
    }

    /**
     * Removes coins: The sum of value from the CashInventory
     *
     * @param valueToRemove
     * @return
     */
    public List<Integer> removeCoins(int valueToRemove) {
        List<Integer> coins=null;
        if (valueToRemove > 0) {
            coins = getCoinList(valueToRemove);
            boolean state = coinStore.isEmpty();
            for (int tempCoin : coins) {
                Integer previous = this.coinStore.put(tempCoin, this.coinStore.get(tempCoin) - 1);
                if (previous == 1) {
                    this.coinStore.remove(tempCoin);
                }
            }
            if (state != coinStore.isEmpty())
                broadcastStateChange();
        }
        return coins!=null?coins:new ArrayList<>();
    }

    /**
     * Check whether the coin list can be created for the given value
     *
     * @param value the value of coins needed
     */
    private List<Integer> getCoinList(int value) {

        int[] coinDenominations = coinStore.keySet().stream().mapToInt(Integer::intValue).toArray();
        int[] coinQuantity = coinStore.values().stream().mapToInt(Integer::intValue).toArray();

        List<Integer> coinList = new ArrayList<>();

        createCoinList(value, coinDenominations, coinQuantity, coinList, 0);

        return coinList;
    }

    /**
     * Recursive algorithm to identify whether a coin list can be created for the value
     *
     * @param targetValue
     * @param coinDenominations
     * @param coinQuantity
     * @param listOfCoins
     * @param position
     * @return
     */
    private int createCoinList(int targetValue, int[] coinDenominations, int[] coinQuantity, List<Integer> listOfCoins, int position) {

        //if total is 0 then return
        if (targetValue == 0) {
            return 0;
        }

        var pushedToList = 0;
        for (int counter = position; counter < coinDenominations.length; counter++) {

            int coinDenomination = coinDenominations[counter];
            //Continue if coin is greater than the total
            if (coinDenomination > targetValue) {
                continue;
            }

            //Continue if the coin isn't available to dispense as change
            if (coinQuantity[counter] == 0) {
                continue;
            }

            coinQuantity[counter] -= 1;
            listOfCoins.add(coinDenomination);
            pushedToList = coinDenomination;
            targetValue -= coinDenomination;

            targetValue = createCoinList(targetValue, coinDenominations, coinQuantity, listOfCoins, counter);
            if (targetValue > 0 && pushedToList > 0) {
                    targetValue += pushedToList;
                    listOfCoins.remove(listOfCoins.size() - 1);
                    pushedToList = 0;
            }
        }
        return targetValue;
    }

    public void addObserver(VendingMachineMachineContext vendingMachineMachineContext) {
        this.vendingMachines.add(vendingMachineMachineContext);
    }

    private void broadcastStateChange() {
        String message;
        if (coinStore.isEmpty()) {
            message = "Coins empty";
        } else {
            message = "Coins loaded";
        }
        logger.info(message);
        for (VendingMachineMachineContext vendingMachineMachineContext : this.vendingMachines) {
            vendingMachineMachineContext.update();
        }
    }
}