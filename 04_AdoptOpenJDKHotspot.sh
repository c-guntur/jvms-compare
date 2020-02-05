#!/bin/bash

source env.sh

echo "--------------------------------"
echo "04. Beginning Adopt OpenJDK with Hotspot benchmarks ..."
export JAVA_HOME=${ADOPT_HOTSPOT_11_HOME}
export PATH=$JAVA_HOME/bin:$PATH

echo "JAVA_HOME=${JAVA_HOME}"
echo "Java Version"
echo "------------"
echo `java -version`
echo "------------"

export MAVEN_OPTS="-Xms1024m -Xmx1024m"

#IntList
echo "  1. IntListFilter benchmarks :: less output/04_AdoptOpenJDKHotspotIntListFilter.txt"
mvn -P AdoptOpenJDKHotspot,IntList clean test exec:exec -t toolchains.xml > output/04_AdoptOpenJDKHotspotIntListFilter.txt 2>&1
echo "         Completed at $(date "+%Y-%m-%d %H:%M:%S")"
echo "  2. IntListSum benchmarks :: less output/04_AdoptOpenJDKHotspotIntListSum.txt"
mvn -P AdoptOpenJDKHotspot,IntList clean test exec:exec@sum -t toolchains.xml > output/04_AdoptOpenJDKHotspotIntListSum.txt 2>&1
echo "         Completed at $(date "+%Y-%m-%d %H:%M:%S")"
echo "  3. IntListTransform benchmarks :: less output/04_AdoptOpenJDKHotspotIntListTransform.txt"
mvn -P AdoptOpenJDKHotspot,IntList clean test exec:exec@transform -t toolchains.xml > output/04_AdoptOpenJDKHotspotIntListTransform.txt 2>&1
echo "         Completed at $(date "+%Y-%m-%d %H:%M:%S")"
#Person
echo "  4. PersonFilter benchmarks :: less output/04_AdoptOpenJDKHotspotPersonFilterOnly.txt"
mvn -P AdoptOpenJDKHotspot,Person clean test exec:exec -t toolchains.xml > output/04_AdoptOpenJDKHotspotPersonFilterOnly.txt 2>&1
echo "         Completed at $(date "+%Y-%m-%d %H:%M:%S")"
echo "  5. PersonFilterAndGroup benchmarks :: less output/04_AdoptOpenJDKHotspotPersonFilterAndGroup.txt"
mvn -P AdoptOpenJDKHotspot,Person clean test exec:exec@filterAndGroup -t toolchains.xml > output/04_AdoptOpenJDKHotspotPersonFilterAndGroup.txt 2>&1
echo "         Completed at $(date "+%Y-%m-%d %H:%M:%S")"
echo "  6. PersonIntSummaryStatistics benchmarks :: less output/04_AdoptOpenJDKHotspotPersonIntSummaryStats.txt"
mvn -P AdoptOpenJDKHotspot,Person clean test exec:exec@intSummaryStats -t toolchains.xml > output/04_AdoptOpenJDKHotspotPersonIntSummaryStats.txt 2>&1
echo "         Completed at $(date "+%Y-%m-%d %H:%M:%S")"
echo "  7. PersonCombinedSummaryStatistics benchmarks :: less output/04_AdoptOpenJDKHotspotPersonCombinedSummaryStats.txt"
mvn -P AdoptOpenJDKHotspot,Person clean test exec:exec@combinedSummaryStats -t toolchains.xml > output/04_AdoptOpenJDKHotspotPersonCombinedSummaryStats.txt 2>&1
echo "         Completed at $(date "+%Y-%m-%d %H:%M:%S")"
echo "Completed Adopt OpenJDK with Hotspot benchmarks."
echo "--------------------------------"
echo

