#!/bin/bash

# Gradle Wrapper Initialization Script
# This script will initialize the Gradle wrapper properly

echo "Initializing Gradle Wrapper..."

# Create gradle directory if it doesn't exist
mkdir -p gradle/wrapper

# Download the proper Gradle wrapper jar (version 8.4)
echo "Downloading Gradle wrapper JAR..."
curl -L -o gradle/wrapper/gradle-wrapper.jar \
    https://raw.githubusercontent.com/gradle/gradle/v8.4.0/gradle/wrapper/gradle-wrapper.jar

# Update gradle-wrapper.properties with correct version
echo "Updating gradle-wrapper.properties..."
cat > gradle/wrapper/gradle-wrapper.properties << EOF
distributionBase=GRADLE_USER_HOME
distributionPath=wrapper/dists
distributionUrl=https\://services.gradle.org/distributions/gradle-8.4-bin.zip
networkTimeout=10000
validateDistributionUrl=true
zipStoreBase=GRADLE_USER_HOME
zipStorePath=wrapper/dists
EOF

# Make gradlew executable
echo "Making gradlew executable..."
chmod +x gradlew

echo "Gradle wrapper initialization complete!"
echo "Now you can run: ./gradlew build"