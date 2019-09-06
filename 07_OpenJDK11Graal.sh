#!/bin/bash

export JAVA_HOME=/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home
export MAVEN_OPTS=
#IntList
mvn -P OpenJDK11Graal clean test exec:exec -t toolchains.xml > output/07_OpenJDK11GraalIntListFilter.txt 2>&1
mvn -P OpenJDK11Graal clean test exec:exec@sum -t toolchains.xml > output/07_OpenJDK11GraalIntListSum.txt 2>&1
mvn -P OpenJDK11Graal clean test exec:exec@transform -t toolchains.xml > output/07_OpenJDK11GraalIntListTransform.txt 2>&1
#Person
mvn -P OpenJDK11Graal clean test exec:exec@filterOnly -t toolchains.xml > output/07_OpenJDK11GraalPersonFilterOnly.txt 2>&1
mvn -P OpenJDK11Graal clean test exec:exec@filterAndGroup -t toolchains.xml > output/07_OpenJDK11GraalPersonFilterAndGroup.txt 2>&1
mvn -P OpenJDK11Graal clean test exec:exec@intSummaryStats -t toolchains.xml > output/07_OpenJDK11GraalPersonIntSummaryStats.txt 2>&1
mvn -P OpenJDK11Graal clean test exec:exec@combinedSummaryStats -t toolchains.xml > output/07_OpenJDK11GraalPersonCombinedSummaryStats.txt 2>&1
