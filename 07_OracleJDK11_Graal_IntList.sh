#!/bin/bash

export JAVA_HOME=/Library/Java/JavaVirtualMachines/oraclejdk-11.0.4.jdk/Contents/Home
export MAVEN_OPTS="-XX:+UnlockExperimentalVMOptions -XX:+EnableJVMCI -XX:+UseJVMCICompiler"
mvn -P OracleJava11IntList clean test exec:exec -t toolchains.xml > output/07_OracleJDK11_Graal_IntListOutput.txt 2>&1
