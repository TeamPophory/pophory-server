#!/bin/bash

# Check if an argument is provided
if [ -z "$1" ]; then
    echo "Usage: $0 <environment>"
    exit 1
fi

# Set the environment variable
ENV=$1

# Run the Gradle command
./gradlew clean build -x test

# Check if the gradle build command succeeded
if [ $? -ne 0 ]; then
    echo "Gradle build failed!"
    exit 2
fi

# Change directory and run the Java command
cd build/libs
nohup java -Dspring.profiles.active=${ENV} -jar pophoryserver-0.0.1-SNAPSHOT.jar --server.port=8080  &

echo "Server started with profile: ${ENV}"

