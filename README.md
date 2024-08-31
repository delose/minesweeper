# Minesweeper

This is a simple Java implementation of the classic Minesweeper game. 

## Table of Contents
- [Installation](#installation)

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

1. **Build the project:**

    ```sh
    mvn clean compile
    ```

2. **Run the game:**

    ```sh
    mvn exec:java -Dexec.mainClass="com.delose.MinesweeperApp"
    ```


# Developer notes

## Initialize project with Maven

    ```sh
    mvn archetype:generate -DgroupId=com.delose.minesweeper -DartifactId=minesweeper -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
    ```

