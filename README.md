# VendingMachine

###Project Libraries 
* JDK 11
* Gradle 
* Junit 5
* Spring boot

### How to build this project
./gradlew clean build

### How to run this project using CLI
* java -jar build/libs/vendingmachine-0.0.1-SNAPSHOT.jar
* Postman collection VendingMachine.postman_collection.json in the root folder can be used to test the REST API


###This component provides APIs which:
*	Initialise the vending machine to a known state, for use when the machine is set up. This should include setting the initial float (the coins placed in the machine for customer change) which should be accepted as a parameter
*	Register coins that have been deposited by a user
*	Produce a collection of coins that sum to a particular value (accepted as a parameter) from the coins available in the machine (for the purpose of returning change to the user), and remove the coins from the machine.

###Assumptions
* Coins denomination, and the number of coins is integer
* Coins are dispensed only if they are available.
* More Junits to be added

###Design Decisions
* Considered any denomination value. As an improvement it would be nice to keep it as enum of predefined denominations as they wouldn't change
* State Pattern is used to define the state and behaviour changes based on the state.
* Observer Pattern is used to track the Coin
* System.out.println() is kept for the test harness.

##REST API 

POST public/v1/initialize
http://localhost:9090/vendingmachine/public/v1/initialize

cURL Command :-
````
curl --location --request POST 'http://localhost:9090/vendingmachine/public/v1/initialize' \
--header 'Content-Type: application/json' \
--data-raw ' [
        {
            "denomination": "20",
            "quantity": "2"
        },
        {
            "denomination": "10",
            "quantity": "1"
        }
]
'
````
Request Sample :-
````
[
    {
        "denomination": "20",
        "quantity": "2"
    },
    {
        "denomination": "10",
        "quantity": "1"
    }
]
````
Response Sample :-
````
{
    "status": "INITIALISED",
    "coinStore": {
        "20": 2,
        "10": 1
    }
}
````
POST public/v1/register
http://localhost:9090/vendingmachine/public/v1/register

cURL Command :-
````
curl --location --request POST 'http://localhost:9090/vendingmachine/public/v1/register' \
--header 'Content-Type: application/json' \
--data-raw ' [
{
"denomination": "20",
"quantity": "2"
},
{
"denomination": "10",
"quantity": "1"
}
]

'
````
Request Sample :-
````
[
    {
        "denomination": "20",
        "quantity": "2"
    },
    {
        "denomination": "10",
        "quantity": "1"
    }
]
````
Response Sample :-
````
{
    "status": "INITIALISED",
    "coinStore": {
        "20": 4,
        "10": 2
    }
}
````

Error response sample :-
````
{
    "message": "Cannot register, initialize the vending machine before registering",
    "serviceCode": "002",
    "timestamp": "2021-05-23 02:49:07"
}
````
GET public/v1/dispense

http://localhost:9090/vendingmachine/public/v1/dispense?amountToDispense=30

cURL Command :-
````
curl --location --request GET 'http://localhost:9090/vendingmachine/public/v1/dispense?amountToDispense=30' \
--header 'Content-Type: application/json' \
--data-raw ''
````
Sample Response :-
````
[
    20,
    10
]
````
Sample Error response if not able to dispense coin :- 
````
{
    "message": "No coins to dispense",
    "serviceCode": "002",
    "timestamp": "2021-05-23 03:42:13"
}
````

Interactive test-harness used to play with the code

    > Task :VendingMachineCLIApplication.main()
    15:53:53.199 [main] ERROR com.example.vendingmachine.VendingMachineCLIApplication - No console
    15:53:53.203 [main] INFO com.example.vendingmachine.coin.CoinInventory - Coins empty
    
    
    >> Select an option <<
    1:      Initialise the vending machine and set the initial coins
    9:      Exit
    1
    >> Enter coin denomination as integer (or X to continue):
    30
    >> Enter number of coins for 30:
    3
    >> Enter coin denomination as integer (or X to continue):
    20
    >> Enter number of coins for 20:
    2
    >> Enter coin denomination as integer (or X to continue):
    x
    15:54:38.992 [main] INFO com.example.vendingmachine.state.Uninitialised - Switching to INITIALISED-EMPTY state
    15:54:38.992 [main] INFO com.example.vendingmachine.coin.CoinInventory - Coins loaded
    15:54:38.993 [main] INFO com.example.vendingmachine.context.VendingMachineMachineContext - Switching to INITIALISED
    
    
    >> Select an option <<
    2:      Register coins that have been deposited by a user
    3:      Produce a collection of coins that sum to a particular value
    9:      Exit
    2
    >> Enter coin denomination as integer (or X to continue):
    10
    >> Enter number of coins for 10:
    3
    >> Enter coin denomination as integer (or X to continue):
    x
    
    
    >> Select an option <<
    2:      Register coins that have been deposited by a user
    3:      Produce a collection of coins that sum to a particular value
    9:      Exit
    3
    >> Enter coin value to dispense as integer:
    50
    Removed the following coins from the machine [30, 20]
    
    
    >> Select an option <<
    2:      Register coins that have been deposited by a user
    3:      Produce a collection of coins that sum to a particular value
    9:      Exit
