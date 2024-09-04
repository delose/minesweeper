#!/bin/bash

# Function to check if a command exists
command_exists() {
    command -v "$1" >/dev/null 2>&1
}

# Check if Maven is installed
if ! command_exists mvn; then
    echo "Maven is not installed. Please install Maven to proceed."
    exit 1
fi

# Check if Java is installed
if ! command_exists java; then
    echo "Java is not installed. Please install Java to proceed."
    exit 1
fi

# Check if JAVA_HOME is set
# if [ -z "$JAVA_HOME" ]; then
#     echo "JAVA_HOME is not set. Please set JAVA_HOME to the root of your Java installation."
#     exit 1
# fi

echo "Environment check passed. Maven and Java are installed."