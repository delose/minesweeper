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

1. **Run the game using the provided script:**

### run_minesweeper.sh (for Linux/MacOS)
    ```sh
    ./run_minesweeper.sh
    ```

### run_minesweeper.bat (for Windows)
    ```sh
    ./run_minesweeper.bat
    ```

   This script will automatically compile the project and run the Minesweeper game.

2. **Build the project:**

    ```sh
    mvn clean compile
    ```

3. **Run the game:**

    ```sh
    mvn exec:java -Dexec.mainClass="com.delose.minesweeper.App"
    ```

4. **Run the tests:**

    ```sh
    mvn test
    ```

## Developer Notes

    **Initialize project with Maven:**

    ```sh
    mvn archetype:generate -DgroupId=com.delose.minesweeper -DartifactId=minesweeper -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
    ```

## Coding Practices

- **Test-Driven Development (TDD):** The game was developed with a focus on TDD, ensuring that tests were written before the implementation of core features.
- **Code Readability:** Emphasis was placed on writing clean and maintainable code, with meaningful variable names, and methods that perform a single task.
- **Small Methods:** Methods were kept small and focused, generally adhering to a limit of 50 lines or fewer, to ensure clarity and ease of understanding.
- **Single Responsibility Principle (SRP):** Each class and method is designed to have a single responsibility, making the code easier to maintain and extend.

## Design Patterns Used

- **Model-View-Controller (MVC):** The game architecture follows the MVC pattern, separating concerns into three components:
  - **Model (`Minefield`, `Cell`):** Handles the game's data and business logic.
  - **View (`DisplayManager`):** Manages the display of the game grid and user interaction.
  - **Controller (`GameController`):** Handles user input and updates the model and view accordingly.
  - **Helper Class:** The `GameSetupHelper` class is used to encapsulate setup and initialization logic, promoting separation of concerns and improving the readability of the main method.


## Limitations

- **Command-Line Interface Only:** The game currently supports only a text-based interface. There is no graphical user interface (GUI).
- **No Save/Load Functionality:** The game does not support saving and loading of game states.
- **Fixed Maximum Mine Ratio:** The maximum number of mines is currently capped at 35% of the grid, which might limit customization for some users.

## Assumptions

- **Input Format:** User input is assumed to be in the format "A1", where "A" represents the row and "1" represents the column.
- **Grid Size Limits:** The game assumes that the grid size and number of mines are provided within reasonable bounds (e.g., grid sizes typically between 4x4 and 10x10).
- **Java 8+:** The code assumes that it is running on Java 8 or later, taking advantage of features like lambdas and streams.
- **Single Player:** The game is designed for single-player interaction, with no multiplayer or networked features.