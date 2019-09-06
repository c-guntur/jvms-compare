#!/bin/bash

export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-11.0.2.jdk/Contents/Home
export MAVEN_OPTS=
#IntList
mvn -P OpenJDK11Hotspot,IntList clean test exec:exec -t toolchains.xml > output/06_OpenJDK11HotspotIntListFilter.txt 2>&1
mvn -P OpenJDK11Hotspot,IntList clean test exec:exec@sum -t toolchains.xml > output/06_OpenJDK11HotspotIntListSum.txt 2>&1
mvn -P OpenJDK11Hotspot,IntList clean test exec:exec@transform -t toolchains.xml > output/06_OpenJDK11HotspotIntListTransform.txt 2>&1
#Person
mvn -P OpenJDK11Hotspot,Person clean test exec:exec -t toolchains.xml > output/06_OpenJDK11HotspotPersonFilterOnly.txt 2>&1
mvn -P OpenJDK11Hotspot,Person clean test exec:exec@filterAndGroup -t toolchains.xml > output/06_OpenJDK11HotspotPersonFilterAndGroup.txt 2>&1
mvn -P OpenJDK11Hotspot,Person clean test exec:exec@intSummaryStats -t toolchains.xml > output/06_OpenJDK11HotspotPersonIntSummaryStats.txt 2>&1
mvn -P OpenJDK11Hotspot,Person clean test exec:exec@combinedSummaryStats -t toolchains.xml > output/06_OpenJDK11HotspotPersonCombinedSummaryStats.txt 2>&1
