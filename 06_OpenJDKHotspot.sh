#!/bin/bash

./env.sh

echo "Beginning OpenJDK benchmarks ..."
export JAVA_HOME=${OPENJDK_11_HOME}
export MAVEN_OPTS=
#IntList
echo "  1. IntListFilter benchmarks :: less output/06_OpenJDKHotspotIntListFilter.txt"
mvn -P OpenJDKHotspot,IntList clean test exec:exec -t toolchains.xml > output/06_OpenJDKHotspotIntListFilter.txt 2>&1
echo "  2. IntListSum benchmarks :: less output/06_OpenJDKHotspotIntListSum.txt"
mvn -P OpenJDKHotspot,IntList clean test exec:exec@sum -t toolchains.xml > output/06_OpenJDKHotspotIntListSum.txt 2>&1
echo "  3. IntListTransform benchmarks :: less output/06_OpenJDKHotspotIntListTransform.txt"
mvn -P OpenJDKHotspot,IntList clean test exec:exec@transform -t toolchains.xml > output/06_OpenJDKHotspotIntListTransform.txt 2>&1
#Person
echo "  4. PersonFilter benchmarks :: less output/06_OpenJDKHotspotPersonFilterOnly.txt"
mvn -P OpenJDKHotspot,Person clean test exec:exec -t toolchains.xml > output/06_OpenJDKHotspotPersonFilterOnly.txt 2>&1
echo "  5. PersonFilterAndGroup benchmarks :: less output/06_OpenJDKHotspotPersonFilterAndGroup.txt"
mvn -P OpenJDKHotspot,Person clean test exec:exec@filterAndGroup -t toolchains.xml > output/06_OpenJDKHotspotPersonFilterAndGroup.txt 2>&1
echo "  6. PersonIntSummaryStatistics benchmarks :: less output/06_OpenJDKHotspotPersonIntSummaryStats.txt"
mvn -P OpenJDKHotspot,Person clean test exec:exec@intSummaryStats -t toolchains.xml > output/06_OpenJDKHotspotPersonIntSummaryStats.txt 2>&1
echo "  7. PersonCombinedSummaryStatistics benchmarks :: less output/06_OpenJDKHotspotPersonCombinedSummaryStats.txt"
mvn -P OpenJDKHotspot,Person clean test exec:exec@combinedSummaryStats -t toolchains.xml > output/06_OpenJDKHotspotPersonCombinedSummaryStats.txt 2>&1
echo "Completed OpenJDK benchmarks."
echo "--------------------------------"
echo

