#!/bin/bash

source env.sh

echo "--------------------------------"
echo "02. Beginning GraalVM EE benchmarks ..."

export GRAALVM_HOME=/Users/cguntur/Tools/jdks/graalvm-ee-java11-19.3.0.2/Contents/Home/
export GRAAL_HOME=/Users/cguntur/Tools/jdks/graalvm-ee-java11-19.3.0.2/Contents/Home/
export JAVA_HOME=${GRAALVM_EE_19_HOME}
export PATH=$JAVA_HOME/bin:$PATH

echo "JAVA_HOME=${JAVA_HOME}"
echo "Java Version"
echo "------------"
echo `java -version`
echo "------------"

export MAVEN_OPTS=

#IntList
echo "  1. IntListFilter benchmarks :: less output/02_GraalEEIntListFilter.txt"
mvn -P GraalEE,IntList clean test exec:exec -t toolchains.xml > output/02_GraalEEIntListFilter.txt 2>&1
echo "         Completed at ${TIMESTAMP}"
echo "  2. IntListSum benchmarks :: less output/02_GraalEEIntListSum.txt"
mvn -P GraalEE,IntList clean test exec:exec@sum -t toolchains.xml > output/02_GraalEEIntListSum.txt 2>&1
echo "         Completed at ${TIMESTAMP}"
echo "  3. IntListTransform benchmarks :: less output/02_GraalEEIntListTransform.txt"
mvn -P GraalEE,IntList clean test exec:exec@transform -t toolchains.xml > output/02_GraalEEIntListTransform.txt 2>&1
echo "         Completed at ${TIMESTAMP}"
#Person
echo "  4. PersonFilter benchmarks :: less output/02_GraalEEPersonFilterOnly.txt"
mvn -P GraalEE,Person clean test exec:exec -t toolchains.xml > output/02_GraalEEPersonFilterOnly.txt 2>&1
echo "         Completed at ${TIMESTAMP}"
echo "  5. PersonFilterAndGroup benchmarks :: less output/02_GraalEEPersonFilterAndGroup.txt"
mvn -P GraalEE,Person clean test exec:exec@filterAndGroup -t toolchains.xml > output/02_GraalEEPersonFilterAndGroup.txt 2>&1
echo "         Completed at ${TIMESTAMP}"
echo "  6. PersonIntSummaryStatistics benchmarks :: less output/02_GraalEEPersonIntSummaryStats.txt"
mvn -P GraalEE,Person clean test exec:exec@intSummaryStats -t toolchains.xml > output/02_GraalEEPersonIntSummaryStats.txt 2>&1
echo "         Completed at ${TIMESTAMP}"
echo "  7. PersonCombinedSummaryStatistics benchmarks :: less output/02_GraalEEPersonCombinedSummaryStats.txt"
mvn -P GraalEE,Person clean test exec:exec@combinedSummaryStats -t toolchains.xml > output/02_GraalEEPersonCombinedSummaryStats.txt 2>&1
echo "         Completed at ${TIMESTAMP}"
echo "Completed GraalVM EE benchmarks."
echo "--------------------------------"
echo

