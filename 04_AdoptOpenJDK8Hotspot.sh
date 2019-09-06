#!/bin/bash

export JAVA_HOME=/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home
export MAVEN_OPTS=
#IntList
mvn -P AdoptOpenJDK8Hotspot,IntList clean test exec:exec -t toolchains.xml > output/04_AdoptOpenJDK8HotspotIntListFilter.txt 2>&1
mvn -P AdoptOpenJDK8Hotspot,IntList clean test exec:exec@sum -t toolchains.xml > output/04_AdoptOpenJDK8HotspotIntListSum.txt 2>&1
mvn -P AdoptOpenJDK8Hotspot,IntList clean test exec:exec@transform -t toolchains.xml > output/04_AdoptOpenJDK8HotspotIntListTransform.txt 2>&1
#Person
mvn -P AdoptOpenJDK8Hotspot,Person clean test exec:exec -t toolchains.xml > output/04_AdoptOpenJDK8HotspotPersonFilterOnly.txt 2>&1
mvn -P AdoptOpenJDK8Hotspot,Person clean test exec:exec@filterAndGroup -t toolchains.xml > output/04_AdoptOpenJDK8HotspotPersonFilterAndGroup.txt 2>&1
mvn -P AdoptOpenJDK8Hotspot,Person clean test exec:exec@intSummaryStats -t toolchains.xml > output/04_AdoptOpenJDK8HotspotPersonIntSummaryStats.txt 2>&1
mvn -P AdoptOpenJDK8Hotspot,Person clean test exec:exec@combinedSummaryStats -t toolchains.xml > output/04_AdoptOpenJDK8HotspotPersonCombinedSummaryStats.txt 2>&1
