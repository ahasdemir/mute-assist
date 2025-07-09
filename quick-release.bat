@echo off
setlocal enabledelayedexpansion

:: Quick Release Script for Mute Assist Mod (Windows)
:: Usage: quick-release.bat [version] [message]
:: Example: quick-release.bat 0.1.3 "Fixed configuration bug"

echo.
echo 🚀 Mute Assist Mod - Quick Release Script (Windows)
echo ==========================================

set NEW_VERSION=%1
set RELEASE_MESSAGE=%2

if "%NEW_VERSION%"=="" (
    echo ❌ Error: Version number required
    echo Usage: %0 ^<version^> [message]
    echo Example: %0 0.1.3 "Fixed configuration bug"
    exit /b 1
)

if "%RELEASE_MESSAGE%"=="" (
    set RELEASE_MESSAGE=Release v%NEW_VERSION%
)

:: Validate version format (basic check)
echo %NEW_VERSION% | findstr /R "^[0-9]\+\.[0-9]\+\.[0-9]\+$" >nul
if errorlevel 1 (
    echo ❌ Error: Version must be in format X.Y.Z (e.g., 0.1.3)
    exit /b 1
)

:: Check if we're in the right directory
if not exist "gradle.properties" (
    echo ❌ Error: Must be run from the mod root directory
    exit /b 1
)
if not exist "src\main\resources\fabric.mod.json" (
    echo ❌ Error: Must be run from the mod root directory
    exit /b 1
)

echo 📋 Release Summary:
echo   Version: v%NEW_VERSION%
echo   Message: %RELEASE_MESSAGE%
echo.

:: Check git status
git status --porcelain > temp_status.txt
set /p GIT_STATUS=<temp_status.txt
del temp_status.txt

if not "%GIT_STATUS%"=="" (
    echo ⚠️  Warning: You have uncommitted changes
    git status --short
    echo.
    set /p CONTINUE="Continue anyway? (y/N): "
    if /i not "!CONTINUE!"=="y" (
        echo ❌ Aborted
        exit /b 1
    )
)

echo 🔧 Step 1: Updating version numbers...

:: Update gradle.properties
powershell -Command "(Get-Content gradle.properties) -replace 'mod_version=.*', 'mod_version=%NEW_VERSION%' | Set-Content gradle.properties"
echo   ✅ Updated gradle.properties

:: Update MuteAssistCommand.java
powershell -Command "(Get-Content src\client\java\com\muteassist\MuteAssistCommand.java) -replace 'Mute Assist Mod v[0-9.]+', 'Mute Assist Mod v%NEW_VERSION%' | Set-Content src\client\java\com\muteassist\MuteAssistCommand.java"
echo   ✅ Updated MuteAssistCommand.java

:: Update README.md
powershell -Command "(Get-Content README.md) -replace 'mute-assist-v[0-9.]+\.jar', 'mute-assist-v%NEW_VERSION%.jar' | Set-Content README.md"
echo   ✅ Updated README.md

:: Update build scripts
powershell -Command "(Get-Content build.sh) -replace 'mute-assist-v[0-9.]+\.jar', 'mute-assist-v%NEW_VERSION%.jar' | Set-Content build.sh"
powershell -Command "(Get-Content build.bat) -replace 'mute-assist-v[0-9.]+\.jar', 'mute-assist-v%NEW_VERSION%.jar' | Set-Content build.bat"
echo   ✅ Updated build scripts

echo 🏗️  Step 2: Building mod...
call gradlew.bat build --quiet

if errorlevel 1 (
    echo ❌ Build failed
    exit /b 1
)

echo   ✅ Build successful

echo 📦 Step 3: Committing changes...
git add -A
git commit -m "%RELEASE_MESSAGE%" -m "" -m "- Updated version to v%NEW_VERSION%" -m "- Built and tested mod JAR files" -m "- Updated documentation and scripts"

echo   ✅ Changes committed

echo 🏷️  Step 4: Creating tag...
git tag -a "v%NEW_VERSION%" -m "%RELEASE_MESSAGE%"
echo   ✅ Tag v%NEW_VERSION% created

echo 🚀 Step 5: Pushing to GitHub...
git push origin master
git push origin "v%NEW_VERSION%"
echo   ✅ Pushed to GitHub

echo.
echo 🎉 Release v%NEW_VERSION% completed successfully!
echo 📍 Next steps:
echo   1. GitHub Actions will automatically create the release
echo   2. JAR files will be uploaded automatically
echo   3. Release notes will be generated
echo   4. Check your GitHub repository releases page
echo.
echo 🔗 Built files:
dir build\libs\mute-assist-*.jar

pause
