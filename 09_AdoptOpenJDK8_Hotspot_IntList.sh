#!/bin/bash

export JAVA_HOME=/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home/
export MAVEN_OPTS=
mvn -P AdoptOpenJDK8HotspotIntList clean test exec:exec -t toolchains.xml > output/09_AdoptOpenJDK8_Hotspot_IntListOutput.txt 2>&1
