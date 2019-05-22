#!/bin/bash
echo "build sttart"
mvn clean install -DskipTests
echo "build complete"
sonar:sonar -Dsonar.projectKey=masterService -Dsonar.host.url=http://localhost:9000 -Dsonar.login=27af7a19bba21473c80c05044511e48e37734ebd