#!/bin/bash

export JAVA_HOME=/Library/Java/JavaVirtualMachines/adoptopenjdk-8-openj9.jdk/Contents/Home/
export MAVEN_OPTS=
mvn -P AdoptOpenJDK8OpenJ9IntList clean test exec:exec -t toolchains.xml > output/11_AdoptOpenJDK8_OpenJ9_IntListOutput.txt 2>&1
