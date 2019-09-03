#!/bin/bash

export JAVA_HOME=/Library/Java/JavaVirtualMachines/oraclejdk-11.0.4.jdk/Contents/Home
export MAVEN_OPTS=
mvn -P OracleJava11Person clean test exec:exec -t toolchains.xml > output/06_OracleJDK11_Hotspot_PersonOutput.txt 2>&1
