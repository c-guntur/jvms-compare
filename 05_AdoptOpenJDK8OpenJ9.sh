#!/bin/bash

export JAVA_HOME=/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home
export MAVEN_OPTS=
#IntList
mvn -P AdoptOpenJDK8OpenJ9,IntList clean test exec:exec -t toolchains.xml > output/05_AdoptOpenJDK8OpenJ9IntListFilter.txt 2>&1
mvn -P AdoptOpenJDK8OpenJ9,IntList clean test exec:exec@sum -t toolchains.xml > output/05_AdoptOpenJDK8OpenJ9IntListSum.txt 2>&1
mvn -P AdoptOpenJDK8OpenJ9,IntList clean test exec:exec@transform -t toolchains.xml > output/05_AdoptOpenJDK8OpenJ9IntListTransform.txt 2>&1
#Person
mvn -P AdoptOpenJDK8OpenJ9,Person clean test exec:exec -t toolchains.xml > output/05_AdoptOpenJDK8OpenJ9PersonFilterOnly.txt 2>&1
mvn -P AdoptOpenJDK8OpenJ9,Person clean test exec:exec@filterAndGroup -t toolchains.xml > output/05_AdoptOpenJDK8OpenJ9PersonFilterAndGroup.txt 2>&1
mvn -P AdoptOpenJDK8OpenJ9,Person clean test exec:exec@intSummaryStats -t toolchains.xml > output/05_AdoptOpenJDK8OpenJ9PersonIntSummaryStats.txt 2>&1
mvn -P AdoptOpenJDK8OpenJ9,Person clean test exec:exec@combinedSummaryStats -t toolchains.xml > output/05_AdoptOpenJDK8OpenJ9PersonCombinedSummaryStats.txt 2>&1
