# Scratch Game

Scratch Game writtin with Java. 

## Requirements

- Java 17 or higher
- Maven 3.6 or higher

## Build with Maven

```
mvn clean package
```

## Run the Application

After building, run the application with:

```
java -jar target/scratch-game-1.0-jar-with-dependencies.jar --config config.json --betting-amount <amount>
```
Replace `<amount>` with your desired betting amount.

## Run Tests

```
mvn test
```

## More Information

For detailed information about the game logic, configuration, and requirements, see [`problem_description.md`](problem_description.md). 