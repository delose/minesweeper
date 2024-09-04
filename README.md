# Minesweeper

This is a simple Java implementation of the classic Minesweeper game. 

## Table of Contents
- [Features](#features)
- [Installation](#installation)
- [Running the Game](#running-the-game)
- [Developer Notes](#developer-notes)
- [Coding Practices](#coding-practices)
- [Design Patterns Used](#design-patterns-used)
- [Limitations](#limitations)
- [Assumptions](#assumptions)

## Features

- Customizable grid size and number of mines.
- Text-based interface for user input.
- Displays hints for adjacent mines.
- Automatically reveals adjacent squares when there are no nearby mines.
- Detects game over conditions (mine triggered).
- Detects win condition (all non-mine squares revealed).

## Installation

1. **Clone the repository:**

    ```sh
    git clone https://github.com/delose/minesweeper.git
    cd minesweeper
    ```

2. **Install Maven dependencies:**

    ```sh
    mvn clean install
    ```

## Running the Game

1. **Run the game using the provided script, run_minesweeper.sh (for Linux/MacOS):**

    ```sh
    ./run_minesweeper.sh
    ```

2. **Run the game using the provided script, run_minesweeper.bat (for Windows):**

    ```sh
    ./run_minesweeper.bat
    ```

- These scripts will automatically compile the project and run the Minesweeper game.

3. **Build the project:**

    ```sh
    mvn clean compile
    ```

4. **Run the game:**

    ```sh
    mvn exec:java -Dexec.mainClass="com.delose.minesweeper.App"
    ```

5. **Run the tests using the provided script, test_minesweeper.sh (for Windows):**

    ```sh
    ./test_minesweeper.sh
    ```


6. **Run the tests using the provided script, test_minesweeper.bat (for Linux/MacOS):**

    ```sh
    ./test_minesweeper.bat
    ```

7. **Run the code coverage using the provided script, coverage_minesweeper.sh (for Windows):**

    ```sh
    ./coverage_minesweeper.sh
    ```
8. **Run the code coverage using the provided script, coverage_minesweeper.bat (for Linux/MacOS):**

    ```sh
    ./coverage_minesweeper.bat
    ```

## Developer Notes

1. **Initialize project with Maven:**

    ```sh
    mvn archetype:generate -DgroupId=com.delose.minesweeper -DartifactId=minesweeper -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
    ```

## Coding Practices

- **Test-Driven Development (TDD):** The game was developed with a focus on TDD, ensuring that tests were written before the implementation of core features.
- **Code Readability:** Emphasis was placed on writing clean and maintainable code, with meaningful variable names, and methods that perform a single task.
- **Small Methods:** Methods were kept small and focused, generally adhering to a limit of 50 lines or fewer, to ensure clarity and ease of understanding.
- **Single Responsibility Principle (SRP):** Each class and method is designed to have a single responsibility, making the code easier to maintain and extend.
- **Refactoring for Efficiency:** Methods were refactored to reduce their complexity and improve efficiency. For example, large methods were split into smaller, more manageable ones, and control flow structures were simplified to reduce the number of break and continue statements.
- **Custom Exception Handling:** Custom exceptions were introduced to handle specific error cases, such as invalid user inputs. This made the error handling more robust and allowed the application to continue running smoothly even when errors occurred.
- **Logger Implementation:** A logging utility was implemented using SLF4J to replace System.out.println calls, ensuring that log messages are consistent, configurable, and easily manageable. This also allows for better monitoring and debugging of the application in different environments.
- **Configuration Management:** Hardcoded values like grid size limits and mine ratio were moved to a properties YAML file, making the application more flexible and easier to configure without changing the source code.
- **Nested Test Classes:** Test cases were organized using @Nested classes and @DisplayName annotations, improving test readability and structure. This approach also made it easier to group related tests and clearly define test scenarios.
- **Robust Input Validation:** Input validation was enhanced to handle a variety of edge cases, including non-numeric inputs and out-of-range values. This ensures that the game can handle user input gracefully and continue running without crashing.
- **Automated Code Coverage Reports::** Scripts are provided to automatically run tests and generate code coverage reports, making it easy to monitor and maintain high test coverage.

## Design Patterns Used

- **Model-View-Controller (MVC):** The game architecture follows the MVC pattern, separating concerns into three components:
  - **Model (`Minefield`, `Cell`):** Handles the game's data and business logic.
  - **View (`DisplayManager`):** Manages the display of the game grid and user interaction.
  - **Controller (`GameController`):** Handles user input and updates the model and view accordingly.
- **Singleton:** The `GameController` class is implemented as a Singleton to ensure that there's only one instance of the controller throughout the game's lifecycle. This ensures that the game state is consistent and prevents multiple instances from interfering with each other.
- **Dependency Injection:** The `GameController` class uses dependency injection to inject the `Minefield` and `DisplayManager` instances, promoting loose coupling and making the code more flexible and testable.


## Limitations

- **Command-Line Interface Only:** The game currently supports only a text-based interface. There is no graphical user interface (GUI).
- **No Save/Load Functionality:** The game does not support saving and loading of game states.
- **Fixed Maximum Mine Ratio:** The maximum number of mines is currently capped at 35% of the grid, which might limit customization for some users.

## Assumptions

- **Input Format:** User input is assumed to be in the format "A1", where "A" represents the row and "1" represents the column.
- **Grid Size Limits:** The game assumes that the grid size and number of mines are provided within reasonable bounds (e.g., grid sizes typically between 4x4 and 10x10).
- **Java 8+:** The code assumes that it is running on Java 8 or later, taking advantage of features like lambdas and streams.
- **Single Player:** The game is designed for single-player interaction, with no multiplayer or networked features.