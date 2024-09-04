@echo off

REM Check if Maven is installed
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Maven is not installed. Please install Maven to proceed.
    exit /b 1
)

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Java is not installed. Please install Java to proceed.
    exit /b 1
)

@REM REM Check if JAVA_HOME is set
@REM if "%JAVA_HOME%"=="" (
@REM     echo JAVA_HOME is not set. Please set JAVA_HOME to the root of your Java installation.
@REM     exit /b 1
@REM )

echo Environment check passed. Maven and Java are installed.