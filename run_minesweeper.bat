@echo off

REM Call the environment check script
call check_environment.bat

REM If the check passes, continue with the rest of the script
echo Running the main script...

REM Script to build, package, and run the Minesweeper game

REM Run tests
mvn test

REM Check if tests were successful
IF %ERRORLEVEL% NEQ 0 (
    echo Tests failed. Please check for errors and try again.
    exit /B 1
)

REM Clean, compile, and package the project
mvn clean compile package

REM Check if build and package were successful
IF %ERRORLEVEL% EQU 0 (
    REM Run the game using the packaged JAR file
    java -jar target\minesweeper-1.0-SNAPSHOT.jar
) ELSE (
    echo Build failed. Please check for errors and try again.
    exit /B 1
)