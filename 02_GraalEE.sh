#!/bin/bash

export JAVA_HOME=/Library/Java/JavaVirtualMachines/graalvm-ee-19.2.0/Contents/Home
export MAVEN_OPTS=
#IntList
mvn -P GraalEE,IntList clean test exec:exec -t toolchains.xml > output/02_GraalEEIntListFilter.txt 2>&1
mvn -P GraalEE,IntList clean test exec:exec@sum -t toolchains.xml > output/02_GraalEEIntListSum.txt 2>&1
mvn -P GraalEE,IntList clean test exec:exec@transform -t toolchains.xml > output/02_GraalEEIntListTransform.txt 2>&1
#Person
mvn -P GraalEE,Person clean test exec:exec -t toolchains.xml > output/02_GraalEEPersonFilterOnly.txt 2>&1
mvn -P GraalEE,Person clean test exec:exec@filterAndGroup -t toolchains.xml > output/02_GraalEEPersonFilterAndGroup.txt 2>&1
mvn -P GraalEE,Person clean test exec:exec@intSummaryStats -t toolchains.xml > output/02_GraalEEPersonIntSummaryStats.txt 2>&1
mvn -P GraalEE,Person clean test exec:exec@combinedSummaryStats -t toolchains.xml > output/02_GraalEEPersonCombinedSummaryStats.txt 2>&1
