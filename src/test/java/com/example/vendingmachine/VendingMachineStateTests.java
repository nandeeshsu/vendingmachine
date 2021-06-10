package com.example.vendingmachine;

import com.example.vendingmachine.context.VendingMachineMachine;
import com.example.vendingmachine.context.VendingMachineMachineContext;
import com.example.vendingmachine.state.Initialised;
import com.example.vendingmachine.state.InitialisedEmpty;
import com.example.vendingmachine.state.Uninitialised;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class VendingMachineStateTests {

    @Test
    void shouldStartStateIsUninitialisedState() {
        VendingMachineMachineContext vm = new VendingMachineMachineContext();

        //Started with uninitialised state
        assertThat(vm.getVendingMachineState(), instanceOf(Uninitialised.class));
        assertTrue(vm.getVendingMachineState().dispenseCoins(5).isEmpty());
        vm.getVendingMachineState().uninitialise();

        assertThat(vm.getInitialisedState(), instanceOf(Initialised.class));
    }

    @Test
    void shouldInitialisesToEmptyStateWithoutCoin() {
        VendingMachineMachine vm = new VendingMachineMachineContext();
        //Started in Uninitialised state
        assertThat(vm.getVendingMachineState(), instanceOf(Uninitialised.class));

        //Should be in InitialisedEmpty state
        vm.initialise();
        assertThat(vm.getVendingMachineState(), instanceOf(InitialisedEmpty.class));
    }

    @Test
    void canBeInitialisedWithCoin() {
        VendingMachineMachine vm = new VendingMachineMachineContext();

        Map<Integer,Integer> coinsToAdd = new HashMap<>();
        coinsToAdd.put(1,1000);
        coinsToAdd.put(2,1000);
        coinsToAdd.put(5,1000);
        coinsToAdd.put(10,1000);

        //Should in an uninitialised state, now initialise
        vm.initialiseWithCoin(coinsToAdd);
        assertThat(vm.getVendingMachineState(), instanceOf(Initialised.class));
    }

    @Test
    void canDispenseCoinsWhenInitialised() {
        VendingMachineMachine vm = new VendingMachineMachineContext();

        Map<Integer,Integer> coinsToAdd = new HashMap<>();
        coinsToAdd.put(1,1000);
        coinsToAdd.put(2,1000);
        coinsToAdd.put(5,1000);
        coinsToAdd.put(10,1000);

        //Should be in an uninitialised state, now initialise and set float
        vm.initialiseWithCoin(coinsToAdd);
        assertThat(vm.getVendingMachineState(), instanceOf(Initialised.class));
        List<Integer> dc = vm.dispenseCoins(100);
        assertEquals(10,dc.size());
    }

    @Test
    void canBeUninitialisedWithCoinAndWontDispense() {
        VendingMachineMachine vm = new VendingMachineMachineContext();

        Map<Integer,Integer> coinsToAdd = new HashMap<>();
        coinsToAdd.put(1,1000);
        coinsToAdd.put(2,1000);
        coinsToAdd.put(5,1000);
        coinsToAdd.put(10,1000);

        //Should be in an uninitialised state, now initialise and set coin
        vm.initialiseWithCoin(coinsToAdd);
        vm.uninitialise();
        List<Integer> dc = vm.dispenseCoins(100);
        assertTrue(dc.isEmpty());
    }

    @Test
    void switchesToEmptyStateWhenCoinIsEmptyThenBackToInitialisedWhenToppedUp() {
        VendingMachineMachine vm = new VendingMachineMachineContext();

        Map<Integer,Integer> coinsToAdd = new HashMap<>();
        coinsToAdd.put(1,1000);

        vm.initialiseWithCoin(coinsToAdd);
        assertThat(vm.getVendingMachineState(), instanceOf(Initialised.class));

        vm.dispenseCoins(1000);
        assertThat(vm.getVendingMachineState(), instanceOf(InitialisedEmpty.class));
        vm.registerCoins(coinsToAdd);
        assertThat(vm.getVendingMachineState(), instanceOf(Initialised.class));
    }

    @Test
    void switchesToEmptyStateWhenCoinIsEmptyThenBackToInitialisedWhenToppedUpAndToUninitialisedState() {
        VendingMachineMachine vm = new VendingMachineMachineContext();

        Map<Integer,Integer> coinsToAdd = new HashMap<>();
        coinsToAdd.put(1,1000);

        vm.initialiseWithCoin(coinsToAdd);
        assertThat(vm.getVendingMachineState(), instanceOf(Initialised.class));

        vm.dispenseCoins(1000);
        assertThat(vm.getVendingMachineState(), instanceOf(InitialisedEmpty.class));
        vm.getVendingMachineState().initialise();
        vm.getVendingMachineState().initialiseWithCoin(null);


        assertTrue( vm.getVendingMachineState().dispenseCoins(10).isEmpty() );
        vm.getVendingMachineState().uninitialise();
        assertThat(vm.getVendingMachineState(), instanceOf(Uninitialised.class));
    }
}
