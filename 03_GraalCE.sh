#!/bin/bash

./env.sh

echo "Beginning GraalVM CE benchmarks ..."
export JAVA_HOME=${GRAALVM_CE_19_HOME}
export MAVEN_OPTS=
#IntList
echo "  1. IntListFilter benchmarks :: less output/03_GraalCEIntListFilter.txt"
mvn -P GraalCE,IntList clean test exec:exec -t toolchains.xml > output/03_GraalCEIntListFilter.txt 2>&1
echo "  2. IntListSum benchmarks :: less output/03_GraalCEIntListSum.txt"
mvn -P GraalCE,IntList clean test exec:exec@sum -t toolchains.xml > output/03_GraalCEIntListSum.txt 2>&1
echo "  3. IntListTransform benchmarks :: less output/03_GraalCEIntListTransform.txt"
mvn -P GraalCE,IntList clean test exec:exec@transform -t toolchains.xml > output/03_GraalCEIntListTransform.txt 2>&1
#Person
echo "  4. PersonFilter benchmarks :: less output/03_GraalCEPersonFilterOnly.txt"
mvn -P GraalCE,Person clean test exec:exec -t toolchains.xml > output/03_GraalCEPersonFilterOnly.txt 2>&1
echo "  5. PersonFilterAndGroup benchmarks :: less output/03_GraalCEPersonFilterAndGroup.txt"
mvn -P GraalCE,Person clean test exec:exec@filterAndGroup -t toolchains.xml > output/03_GraalCEPersonFilterAndGroup.txt 2>&1
echo "  6. PersonIntSummaryStatistics benchmarks :: less output/03_GraalCEPersonIntSummaryStats.txt"
mvn -P GraalCE,Person clean test exec:exec@intSummaryStats -t toolchains.xml > output/03_GraalCEPersonIntSummaryStats.txt 2>&1
echo "  7. PersonCombinedSummaryStatistics benchmarks :: less output/03_GraalCEPersonCombinedSummaryStats.txt"
mvn -P GraalCE,Person clean test exec:exec@combinedSummaryStats -t toolchains.xml > output/03_GraalCEPersonCombinedSummaryStats.txt 2>&1
echo "Completed GraalVM EE benchmarks."
echo "--------------------------------"
echo

