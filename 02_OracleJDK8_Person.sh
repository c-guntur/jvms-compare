#!/bin/bash

export JAVA_HOME=/Library/Java/JavaVirtualMachines/oraclejdk1.8.0_221.jdk/Contents/Home
export MAVEN_OPTS=
mvn -P OracleJava8Person clean test exec:exec -t toolchains.xml > output/02_OracleJDK8PersonOutput.txt 2>&1
