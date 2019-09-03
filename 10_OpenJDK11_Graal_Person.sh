#!/bin/bash

export JAVA_HOME=/Library/Java/JavaVirtualMachines/oraclejdk-11.0.4.jdk/Contents/Home
export MAVEN_OPTS="-XX:+UnlockExperimentalVMOptions -XX:+EnableJVMCI -XX:+UseJVMCICompiler"
mvn -P OpenJDK11GraalPerson clean test exec:exec -t toolchains.xml > output/10_OracleJDK11_Graal_PersonOutput.txt 2>&1
