@echo off

REM Call the environment check script
call check_environment.bat

REM If the check passes, continue with the rest of the script
echo Running the main script...

REM Script to run unit tests for the Minesweeper game and generate a code coverage report

REM Run tests and generate the coverage report
mvn clean test jacoco:report

REM Check if tests and coverage report generation were successful
IF %ERRORLEVEL% NEQ 0 (
  echo Tests or coverage report generation failed. Please check for errors and try again.
  exit /b 1
) ELSE (
  echo Tests passed and coverage report generated successfully.
  REM Open the coverage report in the default browser
  start target\site\jacoco\index.html
)