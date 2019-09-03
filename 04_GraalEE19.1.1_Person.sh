#!/bin/bash

export JAVA_HOME=/Library/Java/JavaVirtualMachines/graalvm-ee-19.1.1/Contents/Home/
export MAVEN_OPTS=
mvn -P GraalEEPerson clean test exec:exec -t toolchains.xml > output/04_GraalEEPersonOutput.txt 2>&1
