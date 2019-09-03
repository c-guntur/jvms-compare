#!/bin/bash

export JAVA_HOME=/Library/Java/JavaVirtualMachines/adoptopenjdk-8-openj9.jdk/Contents/Home/
export MAVEN_OPTS=
mvn -P AdoptOpenJDK8OpenJ9Person clean test exec:exec -t toolchains.xml > output/12_AdoptOpenJDK8_OpenJ9_PersonOutput.txt 2>&1
