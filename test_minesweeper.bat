@echo off

REM Script to run unit tests for the Minesweeper game

REM Run tests
mvn test

REM Check if tests were successful
IF %ERRORLEVEL% NEQ 0 (
    echo Tests failed. Please check for errors and try again.
    exit /B 1
)