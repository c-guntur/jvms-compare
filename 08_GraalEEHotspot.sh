#!/bin/bash

source env.sh

echo "--------------------------------"
echo "08. Beginning GraalVM EE (without JVMCI Compiler) benchmarks ..."
export JAVA_HOME=${GRAALVM_EE_19_HOME}
export GRAALVM_HOME=${GRAALVM_CE_19_HOME}
export GRAAL_HOME=${GRAALVM_CE_19_HOME}
export PATH=$JAVA_HOME/bin:$PATH

echo "JAVA_HOME=${JAVA_HOME}"
echo "Java Version"
echo "------------"
echo `java -version`
echo "------------"

export MAVEN_OPTS="-XX:-UseJVMCICompiler"

#IntList
echo "  1. IntListFilter benchmarks :: less output/08_GraalEEHotspotIntListFilter.txt"
mvn -P GraalEEHotspot clean test exec:exec -t toolchains.xml > output/08_GraalEEHotspotIntListFilter.txt 2>&1
echo "         Completed at $(date "+%Y-%m-%d %H:%M:%S")"
echo "  2. IntListSum benchmarks :: less output/08_GraalEEHotspotIntListSum.txt"
mvn -P GraalEEHotspot clean test exec:exec@sum -t toolchains.xml > output/08_GraalEEHotspotIntListSum.txt 2>&1
echo "         Completed at $(date "+%Y-%m-%d %H:%M:%S")"
echo "  3. IntListTransform benchmarks :: less output/08_GraalEEHotspotIntListTransform.txt"
mvn -P GraalEEHotspot clean test exec:exec@transform -t toolchains.xml > output/08_GraalEEHotspotIntListTransform.txt 2>&1
echo "         Completed at $(date "+%Y-%m-%d %H:%M:%S")"
#Person
echo "  4. PersonFilter benchmarks :: less output/08_GraalEEHotspotPersonFilterOnly.txt"
mvn -P GraalEEHotspot clean test exec:exec@filterOnly -t toolchains.xml > output/08_GraalEEHotspotPersonFilterOnly.txt 2>&1
echo "         Completed at $(date "+%Y-%m-%d %H:%M:%S")"
echo "  5. PersonFilterAndGroup benchmarks :: less output/08_GraalEEHotspotPersonFilterAndGroup.txt"
mvn -P GraalEEHotspot clean test exec:exec@filterAndGroup -t toolchains.xml > output/08_GraalEEHotspotPersonFilterAndGroup.txt 2>&1
echo "         Completed at $(date "+%Y-%m-%d %H:%M:%S")"
echo "  6. PersonIntSummaryStatistics benchmarks :: less output/08_GraalEEHotspotPersonIntSummaryStats.txt"
mvn -P GraalEEHotspot clean test exec:exec@intSummaryStats -t toolchains.xml > output/08_GraalEEHotspotPersonIntSummaryStats.txt 2>&1
echo "         Completed at $(date "+%Y-%m-%d %H:%M:%S")"
echo "  7. PersonCombinedSummaryStatistics benchmarks :: less output/08_GraalEEHotspotPersonCombinedSummaryStats.txt"
mvn -P GraalEEHotspot clean test exec:exec@combinedSummaryStats -t toolchains.xml > output/08_GraalEEHotspotPersonCombinedSummaryStats.txt 2>&1
echo "         Completed at $(date "+%Y-%m-%d %H:%M:%S")"
echo "Completed GraalVM EE (without JVMCI Compiler) benchmarks."
echo "--------------------------------"
echo

