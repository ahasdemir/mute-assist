#!/bin/bash

# Quick Release Script for Mute Assist Mod
# Usage: ./quick-release.sh [version] [message]
# Example: ./quick-release.sh 0.1.3 "Fixed configuration bug"

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "${BLUE}🚀 Mute Assist Mod - Quick Release Script${NC}"
echo "=========================================="

# Get parameters
NEW_VERSION=$1
RELEASE_MESSAGE=$2

if [ -z "$NEW_VERSION" ]; then
    echo -e "${RED}❌ Error: Version number required${NC}"
    echo "Usage: $0 <version> [message]"
    echo "Example: $0 0.1.3 'Fixed configuration bug'"
    exit 1
fi

if [ -z "$RELEASE_MESSAGE" ]; then
    RELEASE_MESSAGE="Release v$NEW_VERSION"
fi

# Validate version format
if [[ ! $NEW_VERSION =~ ^[0-9]+\.[0-9]+\.[0-9]+$ ]]; then
    echo -e "${RED}❌ Error: Version must be in format X.Y.Z (e.g., 0.1.3)${NC}"
    exit 1
fi

# Check if we're in the right directory
if [ ! -f "gradle.properties" ] || [ ! -f "src/main/resources/fabric.mod.json" ]; then
    echo -e "${RED}❌ Error: Must be run from the mod root directory${NC}"
    exit 1
fi

echo -e "${YELLOW}📋 Release Summary:${NC}"
echo "  Version: v$NEW_VERSION"
echo "  Message: $RELEASE_MESSAGE"
echo

# Check git status
if [ -n "$(git status --porcelain)" ]; then
    echo -e "${YELLOW}⚠️  Warning: You have uncommitted changes${NC}"
    git status --short
    echo
    read -p "Continue anyway? (y/N): " -n 1 -r
    echo
    if [[ ! $REPLY =~ ^[Yy]$ ]]; then
        echo -e "${RED}❌ Aborted${NC}"
        exit 1
    fi
fi

echo -e "${BLUE}🔧 Step 1: Updating version numbers...${NC}"

# Update gradle.properties
sed -i "s/mod_version=.*/mod_version=$NEW_VERSION/" gradle.properties
echo "  ✅ Updated gradle.properties"

# Update MuteAssistCommand.java
sed -i "s/Mute Assist Mod v[0-9.]\+/Mute Assist Mod v$NEW_VERSION/" src/client/java/com/muteassist/MuteAssistCommand.java
echo "  ✅ Updated MuteAssistCommand.java"

# Update README.md
sed -i "s/mute-assist-v[0-9.]\+\.jar/mute-assist-v$NEW_VERSION.jar/" README.md
echo "  ✅ Updated README.md"

# Update build scripts
sed -i "s/mute-assist-v[0-9.]\+\.jar/mute-assist-v$NEW_VERSION.jar/" build.sh
sed -i "s/mute-assist-v[0-9.]\+\.jar/mute-assist-v$NEW_VERSION.jar/" build.bat
echo "  ✅ Updated build scripts"

echo -e "${BLUE}🏗️  Step 2: Building mod...${NC}"
./gradlew build --quiet

if [ $? -ne 0 ]; then
    echo -e "${RED}❌ Build failed${NC}"
    exit 1
fi

echo "  ✅ Build successful"

echo -e "${BLUE}📦 Step 3: Committing changes...${NC}"
git add -A
git commit -m "$RELEASE_MESSAGE

- Updated version to v$NEW_VERSION
- Built and tested mod JAR files
- Updated documentation and scripts"

echo "  ✅ Changes committed"

echo -e "${BLUE}🏷️  Step 4: Creating tag...${NC}"
git tag -a "v$NEW_VERSION" -m "$RELEASE_MESSAGE"
echo "  ✅ Tag v$NEW_VERSION created"

echo -e "${BLUE}🚀 Step 5: Pushing to GitHub...${NC}"
git push origin master
git push origin "v$NEW_VERSION"
echo "  ✅ Pushed to GitHub"

echo
echo -e "${GREEN}🎉 Release v$NEW_VERSION completed successfully!${NC}"
echo -e "${YELLOW}📍 Next steps:${NC}"
echo "  1. GitHub Actions will automatically create the release"
echo "  2. JAR files will be uploaded automatically"
echo "  3. Release notes will be generated"
echo "  4. Check: https://github.com/$(git config --get remote.origin.url | sed 's/.*github.com[:/]\([^/]*\/[^/]*\).*/\1/' | sed 's/\.git$//')/releases"
echo
echo -e "${BLUE}🔗 Built files:${NC}"
ls -la build/libs/mute-assist-*.jar
