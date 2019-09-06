#!/bin/bash

export JAVA_HOME=/Library/Java/JavaVirtualMachines/graalvm-ce-19.2.0/Contents/Home
export MAVEN_OPTS=
#IntList
mvn -P GraalCE,IntList clean test exec:exec -t toolchains.xml > output/03_GraalCEIntListFilter.txt 2>&1
mvn -P GraalCE,IntList clean test exec:exec@sum -t toolchains.xml > output/03_GraalCEIntListSum.txt 2>&1
mvn -P GraalCE,IntList clean test exec:exec@transform -t toolchains.xml > output/03_GraalCEIntListTransform.txt 2>&1
#Person
mvn -P GraalCE,Person clean test exec:exec -t toolchains.xml > output/03_GraalCEPersonFilterOnly.txt 2>&1
mvn -P GraalCE,Person clean test exec:exec@filterAndGroup -t toolchains.xml > output/03_GraalCEPersonFilterAndGroup.txt 2>&1
mvn -P GraalCE,Person clean test exec:exec@intSummaryStats -t toolchains.xml > output/03_GraalCEPersonIntSummaryStats.txt 2>&1
mvn -P GraalCE,Person clean test exec:exec@combinedSummaryStats -t toolchains.xml > output/03_GraalCEPersonCombinedSummaryStats.txt 2>&1
