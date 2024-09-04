#!/bin/bash

# Call the environment check script
source ./check_environment.sh

# If the check passes, continue with the rest of the script
echo "Running the main script..."

# Script to run unit tests for the Minesweeper game and generate a code coverage report

# Run tests and generate the coverage report
mvn clean test jacoco:report

# Check if tests and coverage report generation were successful
if [ $? -ne 0 ]; then
  echo "Tests or coverage report generation failed. Please check for errors and try again."
  exit 1
else
  echo "Tests passed and coverage report generated successfully."
  # Open the coverage report in the default browser
  if which xdg-open > /dev/null; then
    xdg-open target/site/jacoco/index.html
  elif which gnome-open > /dev/null; then
    gnome-open target/site/jacoco/index.html
  elif which open > /dev/null; then
    open target/site/jacoco/index.html
  else
    echo "Could not detect the web browser to open the report."
  fi
fi