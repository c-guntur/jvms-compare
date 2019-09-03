#!/bin/bash

export JAVA_HOME=/Library/Java/JavaVirtualMachines/graalvm-ce-19.2.0/Contents/Home/
export MAVEN_OPTS=
mvn -P GraalCEIntList clean test exec:exec -t toolchains.xml > output/05_GraalCEIntListOutput.txt 2>&1
