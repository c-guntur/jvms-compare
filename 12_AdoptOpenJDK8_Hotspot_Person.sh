#!/bin/bash

export JAVA_HOME=/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home/
export MAVEN_OPTS=
mvn -P AdoptOpenJDK8HotspotPerson clean test exec:exec -t toolchains.xml > output/12_AdoptOpenJDK8_Hotspot_PersonOutput.txt 2>&1
