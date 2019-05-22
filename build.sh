#!/bin/bash

echo "available options are:"
echo "* build"
echo "* run"
echo "* sonar"
echo "* all"

echo ""
echo "passed arguments are:"
echo $@

if [[ $@ == *'build'* ]] || [[ $@ == *'all'* ]]
then
echo "build start"
mvn clean install -DskipTests
echo "build complete"
fi

if [[ $@ == *'sonar'* ]] || [[ $@ == *'all'* ]]
then
echo "sonar start"
mvn sonar:sonar -Dsonar.projectKey=masterService -Dsonar.host.url=http://localhost:9000 -Dsonar.login=27af7a19bba21473c80c05044511e48e37734ebd
echo "sonar end"
fi

if [[ $@ == *'run'* ]] || [[ $@ == *'all'* ]]
then
echo "running "
echo "ok"

exec java -jar ./target/*.jar
fi
