# Gradle Wrapper Fix Guide

## Issue: Gradle Wrapper JAR Missing

The error `Could not find or load main class org.gradle.wrapper.GradleWrapperMain` occurs when the `gradle-wrapper.jar` file is missing or corrupted.

## Solutions:

### Method 1: Automatic GitHub Actions Fix
1. Go to GitHub Actions tab
2. Run "Setup Gradle Wrapper" workflow
3. This will automatically initialize and commit the proper wrapper files

### Method 2: Manual Fix (Local)
```bash
# Clone the repository
git clone https://github.com/senkuboy0-cyber/surf-android-browser.git
cd surf-android-browser

# Make the initialization script executable
chmod +x init-gradle-wrapper.sh

# Run the initialization script
./init-gradle-wrapper.sh

# Test the setup
./gradlew --version
```

### Method 3: Gradle CLI Fix
```bash
# If you have Gradle installed globally
gradle wrapper --gradle-version 8.4

# Then make it executable
chmod +x gradlew
```

### Method 4: Manual JAR Download
```bash
# Create the directory
mkdir -p gradle/wrapper

# Download the official Gradle wrapper JAR
curl -L -o gradle/wrapper/gradle-wrapper.jar \
    https://raw.githubusercontent.com/gradle/gradle/v8.4.0/gradle/wrapper/gradle-wrapper.jar

# Make gradlew executable
chmod +x gradlew
```

## After Fix:
Test with:
```bash
./gradlew lint
./gradlew build
./gradlew assembleDebug
```

## Files Needed:
- ✅ `gradlew` (executable script)
- ✅ `gradlew.bat` (Windows script)
- ✅ `gradle/wrapper/gradle-wrapper.jar` (main JAR)
- ✅ `gradle/wrapper/gradle-wrapper.properties` (configuration)

## Troubleshooting:
1. Ensure `gradlew` has execute permissions: `chmod +x gradlew`
2. Check Java is installed: `java -version`
3. Verify JAR file exists: `ls -la gradle/wrapper/`

## For CI/CD:
The GitHub Actions workflow `build-apk.yml` will automatically:
- Initialize the Gradle wrapper
- Set proper permissions
- Cache dependencies
- Build the APK successfully