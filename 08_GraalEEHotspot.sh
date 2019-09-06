#!/bin/bash

export JAVA_HOME=/Library/Java/JavaVirtualMachines/graalvm-ee-19.2.0/Contents/Home
export MAVEN_OPTS=
#IntList
mvn -P GraalEEHotspot clean test exec:exec -t toolchains.xml > output/08_GraalEEHotspotIntListFilter.txt 2>&1
#mvn -P GraalEEHotspot clean test exec:exec@sum -t toolchains.xml > output/08_GraalEEHotspotIntListSum.txt 2>&1
#mvn -P GraalEEHotspot clean test exec:exec@transform -t toolchains.xml > output/08_GraalEEHotspotIntListTransform.txt 2>&1
#Person
#mvn -P GraalEEHotspot clean test exec:exec@filterOnly -t toolchains.xml > output/08_GraalEEHotspotPersonFilterOnly.txt 2>&1
#mvn -P GraalEEHotspot clean test exec:exec@filterAndGroup -t toolchains.xml > output/08_GraalEEHotspotPersonFilterAndGroup.txt 2>&1
#mvn -P GraalEEHotspot clean test exec:exec@intSummaryStats -t toolchains.xml > output/08_GraalEEHotspotPersonIntSummaryStats.txt 2>&1
#mvn -P GraalEEHotspot clean test exec:exec@combinedSummaryStats -t toolchains.xml > output/08_GraalEEHotspotPersonCombinedSummaryStats.txt 2>&1
