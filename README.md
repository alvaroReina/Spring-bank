# Spring Banking

This Spring Application provides a REST API that allows the user to create accounts in a specific currency and with a set amount of money, and the possibility of transferring money between them.
 
## Instalation

## Prerequisites
- [Java 8](https://adoptopenjdk.net/?variant=openjdk8&jvmVariant=hotspot).
- [Maven](https://maven.apache.org/install.html)

## Build
Clone the repository:
```
git clone https://github.com/alvaroReina/Spring-bank.git
```
Do a clean installation:
```
mvn clean install
```
Then it can be run from with the following command:
```
java -jar target/bank-1.0.jar
```

## Git repository

I tried to make commits regularly to show how the application was being created. 

Everything is committed to master, in a real life application I would create branches for each new functionality and only merge them using a pull request when the tests meet the project standards.

## Endpoints

METHOD | PATH | DESCRIPTION
------------|-----|------------
GET | /api/accounts | Returns all accounts in the database, mostly for testing purposes.
POST | /api/accounts | Creates a new account, returns a (204 CREATED) response if created.
GET | /api/search?name={name} | Finds for matching account.
POST | /api/transfer | Performs a transference between accounts, requires a 'sender' and 'receiver' name.

## Money and currency

Since the requirements do not mention deposit and withdrawals, the account balance and currency are only set at creation time.  

The system only supports EUR, USD and GBP currencies with a fixed exchange rate. Transfers are calculated using USD as the standard currency.  

The amount of money is stored as a BigDecimal to avoid losing decimal precision. To handle currencies I included a simple Currency class.

## Persistence

This application uses a H2 in memory database to be easily portable as the main purpose of this project is to be a demo.

## Error Handling

The expected exceptions that happen due to the business rules are communicated to the client using a common ApiError DTO, internal and validation errors are communicated to the client using the default exception handler of Spring.

## Testing

This application includes Unit tests covering the requirements and the business logic in the services as well as Component tests that loads the Spring Environment for controllers and persistence.

## Future work

It could be interesting to add authentication and authorization to only allow users to send money. Currently, it only requires the sender and receiver name to authorize a transfer.  

Add persistence to the transferences, that way an User could request the historic of an account.