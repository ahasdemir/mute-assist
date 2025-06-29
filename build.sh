#!/bin/bash
# Build script for Mute Assist Mod
# This script builds the mod and provides helpful information

echo "Building Mute Assist Mod..."
echo

# Clean previous build
./gradlew clean

# Build the mod
./gradlew build

if [ $? -eq 0 ]; then
    echo
    echo "==================================="
    echo "     BUILD SUCCESSFUL!"
    echo "==================================="
    echo
    echo "Mod JAR file created:"
    echo "build/libs/mute-assist-v0.1.0.jar"
    echo
    echo "To install:"
    echo "1. Copy the JAR file to your mods folder"
    echo "2. Make sure Fabric Loader and Fabric API are installed"
    echo "3. Launch Minecraft 1.20.1"
    echo
else
    echo
    echo "==================================="
    echo "     BUILD FAILED!"
    echo "==================================="
    echo
    echo "Please check the error messages above and fix any issues."
    echo
fi
