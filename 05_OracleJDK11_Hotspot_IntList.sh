#!/bin/bash

export JAVA_HOME=/Library/Java/JavaVirtualMachines/oraclejdk-11.0.4.jdk/Contents/Home
export MAVEN_OPTS=
mvn -P OracleJava11IntList clean test exec:exec -t toolchains.xml > output/05_OracleJDK11_Hotspot_IntListOutput.txt 2>&1
