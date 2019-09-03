#!/bin/bash

export JAVA_HOME=/Library/Java/JavaVirtualMachines/graalvm-ee-19.1.1/Contents/Home/
export MAVEN_OPTS=
mvn -P GraalCEPerson clean test exec:exec -t toolchains.xml > output/06_GraalCEPersonOutput.txt 2>&1
