@echo off

REM Call the environment check script
call check_environment.bat

REM If the check passes, continue with the rest of the script
echo Running the main script...

REM Script to run unit tests for the Minesweeper game

REM Run tests
mvn test

REM Check if tests were successful
IF %ERRORLEVEL% NEQ 0 (
    echo Tests failed. Please check for errors and try again.
    exit /B 1
)