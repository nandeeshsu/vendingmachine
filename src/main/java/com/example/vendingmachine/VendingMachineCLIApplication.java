package com.example.vendingmachine;

import com.example.vendingmachine.context.VendingMachineMachine;
import com.example.vendingmachine.context.VendingMachineMachineContext;
import com.example.vendingmachine.exception.VendingMachingException;
import com.example.vendingmachine.state.Initialised;
import com.example.vendingmachine.state.InitialisedEmpty;
import com.example.vendingmachine.state.Uninitialised;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Main class to demonstrate test harness
 */
public class VendingMachineCLIApplication {

    private static final Logger logger = LoggerFactory.getLogger(VendingMachineCLIApplication.class);

    public static void main(String[] args) throws Exception {
        var vm = new VendingMachineCLIApplication();
        vm.run();
    }

    /**
     * Test harness runner
     *
     * @throws Exception
     */
    private void run() throws VendingMachingException {

        var run = true;

        var console = System.console();
        if (console == null) {
            logger.error("No console");
        }

        VendingMachineMachine vendingMachine = new VendingMachineMachineContext();
        while (run) {
            var scanner = new Scanner(System.in);
            Map<Integer, Integer> money;

            displayMainMenu(vendingMachine);
            String optionSelected = getSelectedMainMenu(vendingMachine, scanner);

            switch (optionSelected) {
                case "1":
                    money = addCoins(scanner);
                    vendingMachine.initialiseWithCoin(money);
                    break;
                case "2":
                    money = addCoins(scanner);
                    vendingMachine.registerCoins(money);
                    break;
                case "3":
                    dispense(scanner, vendingMachine);
                    break;
                case "9":
                    run = false;
                    break;
                default:
                    System.out.println("Invalid entry");
            }
        }
    }

    /**
     * Presents menu and dispenses coins from the vending machine
     *
     * @param scanner
     * @param vendingMachine
     */
    private void dispense(Scanner scanner, VendingMachineMachine vendingMachine) {
        System.out.println(">> Enter coin value to dispense as integer:");
        String input = scanner.next();
        try {
            var iInput = Integer.parseInt(input);
            if (iInput > 0) {
                List<Integer> dispenseCoins = vendingMachine.dispenseCoins(iInput);
                if (dispenseCoins.isEmpty()) {
                    System.out.println("Sorry, unable to produce a collection of coins to that amount");
                } else {
                    System.out.println("Removed the following coins from the machine " + dispenseCoins);
                }
            } else {
                System.out.println("Value must be between 1 and " + Integer.MAX_VALUE);
            }

        } catch (NumberFormatException nfe) {
            System.out.println("Value must be a positive whole number between 1 and " + Integer.MAX_VALUE);
        }
    }

    /**
     * Adds coins to the vending machine
     *
     * @param scanner
     */
    private Map<Integer, Integer> addCoins(Scanner scanner) {

        Map<Integer, Integer> mapOfDenominationAndCoin = new HashMap<>();

        String error = "Entered value is invalid, value must be between 1 and " + Integer.MAX_VALUE;
        var denomination = "";
        String numberOfCoins;

        while (!denomination.trim().equalsIgnoreCase("X")) {
            try {
                System.out.println(">> Enter coin denomination as integer (or X to continue):");
                denomination = scanner.next();
                if (denomination.trim().equalsIgnoreCase("X"))
                    continue;
                var userProvidedDenomination = Integer.parseInt(denomination);

                System.out.println(">> Enter number of coins for " + userProvidedDenomination + ":");
                numberOfCoins = scanner.next();
                var userProvidedNumberOfCoins = Integer.parseInt(numberOfCoins);

                if (userProvidedDenomination > 0 && userProvidedNumberOfCoins > 0) {
                    if (mapOfDenominationAndCoin.containsKey(userProvidedDenomination)) {
                        mapOfDenominationAndCoin.put(userProvidedDenomination, mapOfDenominationAndCoin.get(userProvidedDenomination) + Integer.parseInt(numberOfCoins));
                    } else {
                        mapOfDenominationAndCoin.put(Integer.valueOf(denomination), Integer.valueOf(numberOfCoins));
                    }
                } else {
                    System.out.println(error);
                }
            } catch (NumberFormatException nfe) {
                System.out.println(error);
            }
        }
        return mapOfDenominationAndCoin;
    }


    /**
     * Displays the main menu options
     *
     * @param vendingMachine
     */
    private void displayMainMenu(VendingMachineMachine vendingMachine) {

        System.out.println("\n");
        System.out.println(">> Select an option <<");

        if (vendingMachine.getStatus().equals(Uninitialised.STATUS)) {
            System.out.println("1:      Initialise the vending machine and set the initial coins");
        }

        if (vendingMachine.getStatus().equals(Initialised.STATUS) || vendingMachine.getStatus().equals(InitialisedEmpty.STATUS)) {
            System.out.println("2:      Register coins that have been deposited by a user");
        }

        if (vendingMachine.getStatus().equals(Initialised.STATUS)) {
            System.out.println("3:      Produce a collection of coins that sum to a particular value");
        }
        System.out.println("9:      Exit");
    }


    /**
     * Reads the menu selections
     *
     * @param vendingMachine
     * @param scanner
     * @return
     */
    private String getSelectedMainMenu(VendingMachineMachine vendingMachine, Scanner scanner) {

        String optionSelected = scanner.next().trim();
        var result = "0";
        switch (optionSelected) {
            case "1":
                if (vendingMachine.getStatus().equals(Uninitialised.STATUS)) {
                    result = optionSelected;
                }
                break;
            case "2":
                if (vendingMachine.getStatus().equals(Initialised.STATUS) || vendingMachine.getStatus().equals(InitialisedEmpty.STATUS)) {
                    result = optionSelected;
                }
                break;
            case "3":
                if (vendingMachine.getStatus().equals(Initialised.STATUS)) {
                    result = optionSelected;
                }
                break;
            case "9":
                result = optionSelected;
                break;
            default:
                break;
        }
        return result;
    }

}
