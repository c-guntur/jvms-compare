#!/bin/bash

export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-11.0.2.jdk/Contents/Home
export MAVEN_OPTS=
mvn -P OpenJDK11HotspotIntList clean test exec:exec -t toolchains.xml > output/07_OpenJDK11_Hotspot_IntListOutput.txt 2>&1
