#!/bin/bash

export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-11.0.2.jdk/Contents/Home
export MAVEN_OPTS=
mvn -P OpenJDK11HotspotPerson clean test exec:exec -t toolchains.xml > output/08_OpenJDK11_Hotspot_PersonOutput.txt 2>&1
