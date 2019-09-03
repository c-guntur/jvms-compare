#!/bin/bash

export JAVA_HOME=/Library/Java/JavaVirtualMachines/oraclejdk1.8.0_221.jdk/Contents/Home
export MAVEN_OPTS=
mvn -P OracleJava8IntList clean test exec:exec -t toolchains.xml > output/01_OracleJDK8IntListOutput.txt 2>&1
