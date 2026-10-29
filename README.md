# EcoTrack London: Micro-Mobility Rental System

A console-based Java application for renting eco-friendly vehicles, built for a fictional city council initiative to cut traffic and pollution in London. Users choose a vehicle and a number of hours, get a costed receipt, and can return vehicles afterwards. Every action is written to a timestamped log file.

Built as a first-year Object-Oriented Programming project (Computer Programming module, Computer Science and Cyber Security).

## Features

- Five vehicle types: Bike, EBike, EScooter, CargoBike and Moped, each with its own hourly rate
- A 15-vehicle inventory with live availability
- Automatic cost calculation, with a printed receipt
- Custom `RentalException` for invalid actions, such as renting a vehicle that is already out or an unknown ID
- Timestamped logging of rentals, returns and errors to `rental_log.txt`
- 8 JUnit tests covering cost calculation and rent/return behaviour

## OOP concepts and design patterns used

| Concept | Where |
|---|---|
| **Inheritance and abstraction** | Abstract `Vehicle` class, with five subclasses that each define their own `calculateCost()` |
| **Polymorphism** | `calculateCost()` runs the correct version for each vehicle type at runtime; `RentalManager` holds everything as `Vehicle` |
| **Encapsulation** | `id` and `available` are private in `Vehicle`, with access through getters and one setter |
| **Singleton pattern** | `FileLogger`: one shared logger for the whole program |
| **Factory Method pattern** | `VehicleFactory`: all vehicle creation in one place, so adding a type means one new subclass and one new `case` |
| **Custom exceptions** | `RentalException` gives clear, system-specific error messages |

## Project structure

```
src/
  Main.java            Entry point and console menu
  models/              Vehicle, Bike, EBike, EScooter, CargoBike, Moped
  services/            RentalManager, FileLogger, VehicleFactory
  exceptions/          RentalException
test/
  RentalTest.java      JUnit 5 tests
lib/
  junit-platform-console-standalone-1.10.0.jar
diagrams/
  uml-class-diagram.png
  sequence-diagram.jpeg
```

## Diagrams

**Class diagram**

![UML class diagram](diagrams/uml-class-diagram.png)

**Sequence diagram (rental flow)**

![Sequence diagram](diagrams/sequence-diagram.jpeg)

## Requirements

Java JDK 17 or later (developed and tested with Java 21).

## Build and run

From the project root:

```bash
# Compile the app
javac -encoding UTF-8 -d bin $(find src -name "*.java")

# Run it
java -cp bin Main
```

On Windows (Command Prompt), list the files instead of using `find`:

```bat
javac -encoding UTF-8 -d bin src\Main.java src\models\*.java src\services\*.java src\exceptions\*.java
java -cp bin Main
```

## Run the tests

```bash
javac -encoding UTF-8 -d bin -cp lib/junit-platform-console-standalone-1.10.0.jar $(find src test -name "*.java")
java -jar lib/junit-platform-console-standalone-1.10.0.jar -cp bin --scan-classpath
```

All 8 tests should pass.

## Vehicle rates

| Vehicle   | Rate per hour |
|-----------|---------------|
| Bike      | £3.99 |
| EBike     | £8.75 |
| EScooter  | £5.25 |
| CargoBike | £6.99 |
| Moped     | £9.50 |

## Known limitations

These were deliberate scope decisions for the coursework:

- One customer session at a time (no accounts or login; the user just types a name)
- Vehicles are hardcoded at startup instead of loaded from a database
- Cost is calculated but no payment is processed
- No maximum rental duration
- A session allows up to two rentals, and non-numeric hours input is not validated

## Possible improvements

- Load vehicles from a database or file
- Add user registration and booking for future dates
- Add payment handling
- Show only available vehicles, with a short description of each type
- Validate all user input
