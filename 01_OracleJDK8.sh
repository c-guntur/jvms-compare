#!/bin/bash

export JAVA_HOME=/Library/Java/JavaVirtualMachines/oraclejdk1.8.0_221.jdk/Contents/Home
export MAVEN_OPTS=
#IntList
mvn -P OracleJDK8,IntList clean test exec:exec -t toolchains.xml > output/01_OracleJDK8IntListFilter.txt 2>&1
mvn -P OracleJDK8,IntList clean test exec:exec@sum -t toolchains.xml > output/01_OracleJDK8IntListSum.txt 2>&1
mvn -P OracleJDK8,IntList clean test exec:exec@transform -t toolchains.xml > output/01_OracleJDK8IntListTransform.txt 2>&1
#Person
mvn -P OracleJDK8,Person clean test exec:exec -t toolchains.xml > output/01_OracleJDK8PersonFilterOnly.txt 2>&1
mvn -P OracleJDK8,Person clean test exec:exec@filterAndGroup -t toolchains.xml > output/01_OracleJDK8PersonFilterAndGroup.txt 2>&1
mvn -P OracleJDK8,Person clean test exec:exec@intSummaryStats -t toolchains.xml > output/01_OracleJDK8PersonIntSummaryStats.txt 2>&1
mvn -P OracleJDK8,Person clean test exec:exec@combinedSummaryStats -t toolchains.xml > output/01_OracleJDK8PersonCombinedSummaryStats.txt 2>&1
