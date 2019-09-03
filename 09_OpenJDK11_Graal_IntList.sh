#!/bin/bash

export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-11.0.2.jdk/Contents/Home
export MAVEN_OPTS="-XX:+UnlockExperimentalVMOptions -XX:+EnableJVMCI -XX:+UseJVMCICompiler"
mvn -P OpenJDK11GraalIntList clean test exec:exec -t toolchains.xml > output/09_OpenJDK11_Graal_IntListOutput.txt 2>&1
