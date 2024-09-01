#!/bin/bash

# Script to build and run the Minesweeper game

# Run tests
mvn test

# Build the project
mvn clean compile

# Check if build was successful
if [ $? -eq 0 ]; then
  # Run the game
  mvn exec:java -Dexec.mainClass="com.delose.minesweeper.App"
else
  echo "Build failed. Please check for errors and try again."
fi

