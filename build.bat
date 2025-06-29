@echo off
REM Build script for Mute Assist Mod
REM This script builds the mod and copies it to a convenient location

echo Building Mute Assist Mod...
echo.

REM Clean previous build
call gradlew clean

REM Build the mod
call gradlew build

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ===================================
    echo     BUILD SUCCESSFUL!
    echo ===================================
    echo.
    echo Mod JAR file created:
    echo build\libs\mute-assist-v0.1.1.jar
    echo.
    echo To install:
    echo 1. Copy the JAR file to your mods folder
    echo 2. Make sure Fabric Loader and Fabric API are installed
    echo 3. Launch Minecraft 1.20.1
    echo.
) else (
    echo.
    echo ===================================
    echo     BUILD FAILED!
    echo ===================================
    echo.
    echo Please check the error messages above and fix any issues.
    echo.
)

pause
