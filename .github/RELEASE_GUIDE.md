# 🤖 Automated Release System

This repository uses GitHub Actions to automate the build and release process for the Mute Assist mod.

## 🚀 Quick Release Process

### Option 1: Using the Release Script (Recommended)

**Windows:**
```batch
quick-release.bat 0.1.3 "Fixed configuration bug"
```

**Linux/Mac:**
```bash
./quick-release.sh 0.1.3 "Fixed configuration bug"
```

### Option 2: Manual Process

1. **Update version** in `gradle.properties`:
   ```properties
   mod_version=0.1.3
   ```

2. **Commit your changes**:
   ```bash
   git add -A
   git commit -m "Release v0.1.3: Fixed configuration bug"
   ```

3. **Create and push tag**:
   ```bash
   git tag v0.1.3
   git push origin master
   git push origin v0.1.3
   ```

4. **GitHub Actions will automatically**:
   - Build the mod
   - Create GitHub release
   - Upload JAR files
   - Generate release notes

## 🔧 Available Workflows

### 1. **Release Workflow** (`.github/workflows/release.yml`)
- **Trigger**: When you push a version tag (e.g., `v0.1.3`)
- **Actions**:
  - Builds the mod with Gradle
  - Creates GitHub release
  - Uploads `mute-assist-X.X.X.jar` and sources
  - Generates detailed release notes

### 2. **Build & Test Workflow** (`.github/workflows/build-test.yml`)
- **Trigger**: On push to `master`/`main` or pull requests
- **Actions**:
  - Validates code builds correctly
  - Uploads build artifacts for testing
  - Provides quick feedback on code changes

## 📦 What Gets Released

Each release automatically includes:

1. **Main JAR**: `mute-assist-X.X.X.jar` (for players)
2. **Sources JAR**: `mute-assist-X.X.X-sources.jar` (for developers)
3. **Release Notes**: Auto-generated with:
   - Feature descriptions
   - Installation instructions
   - Command documentation
   - Configuration details

## 🎯 Release Notes Template

The workflow generates comprehensive release notes including:

- ✨ **Features**: Key functionality highlights
- 🔧 **Commands**: Available command documentation
- ⚙️ **Configuration**: Setup and customization options
- 📦 **Downloads**: Direct links to JAR files
- 🚀 **Installation**: Step-by-step setup guide
- 📋 **Mappings**: Default reason-duration configurations

## 🛠️ Customizing Releases

### Modify Release Notes

Edit `.github/workflows/release.yml` and update the `body:` section:

```yaml
body: |
  ## 🎯 Your Custom Release Notes
  
  ### New in this version:
  - Your custom content here
```

### Change Trigger Conditions

Modify the workflow trigger:

```yaml
on:
  push:
    tags:
      - 'v*'        # Current: v0.1.3, v1.0.0
      - 'release-*' # Alternative: release-0.1.3
```

### Add Build Steps

Add custom build steps before the release:

```yaml
- name: Run tests
  run: ./gradlew test

- name: Generate documentation
  run: ./gradlew javadoc
```

## 🔍 Monitoring Releases

### Check Workflow Status
1. Go to your repository on GitHub
2. Click **"Actions"** tab
3. View running/completed workflows

### View Release
1. Go to **"Releases"** tab
2. Find your new release
3. Download JAR files
4. Read generated release notes

## 🚨 Troubleshooting

### Build Fails
- Check the **Actions** tab for error logs
- Ensure `gradle.properties` version is updated
- Verify all Java syntax is correct

### Release Not Created
- Confirm tag was pushed: `git push origin v0.1.3`
- Check tag format matches `v*` pattern
- Verify GitHub repository permissions

### JAR Files Missing
- Ensure Gradle build completes successfully
- Check `build/libs/` directory exists
- Verify GitHub Actions has write permissions

## 📝 Version Guidelines

Use [Semantic Versioning](https://semver.org/):

- **MAJOR** (1.0.0): Breaking changes
- **MINOR** (0.1.0): New features
- **PATCH** (0.0.1): Bug fixes

Examples:
- `v0.1.3` → `v0.1.4`: Bug fix
- `v0.1.4` → `v0.2.0`: New feature
- `v0.2.0` → `v1.0.0`: Major rewrite

---

**🎉 Happy releasing!** The automation handles all the tedious work so you can focus on developing awesome features!
