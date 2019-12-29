#!/bin/bash

./env.sh

echo "Beginning Adopt OpenJDK with OpenJ9 benchmarks ..."
export JAVA_HOME=${ADOPT_OPENJ9_11_HOME}
export MAVEN_OPTS=
#IntList
echo "  1. IntListFilter benchmarks :: less output/05_AdoptOpenJDKOpenJ9IntListFilter.txt"
mvn -P AdoptOpenJDKOpenJ9,IntList clean test exec:exec -t toolchains.xml > output/05_AdoptOpenJDKOpenJ9IntListFilter.txt 2>&1
echo "  2. IntListSum benchmarks :: less output/05_AdoptOpenJDKOpenJ9IntListSum.txt"
mvn -P AdoptOpenJDKOpenJ9,IntList clean test exec:exec@sum -t toolchains.xml > output/05_AdoptOpenJDKOpenJ9IntListSum.txt 2>&1
echo "  3. IntListTransform benchmarks :: less output/05_AdoptOpenJDKOpenJ9IntListTransform.txt"
mvn -P AdoptOpenJDKOpenJ9,IntList clean test exec:exec@transform -t toolchains.xml > output/05_AdoptOpenJDKOpenJ9IntListTransform.txt 2>&1
#Person
echo "  4. PersonFilter benchmarks :: less output/05_AdoptOpenJDKOpenJ9PersonFilterOnly.txt"
mvn -P AdoptOpenJDKOpenJ9,Person clean test exec:exec -t toolchains.xml > output/05_AdoptOpenJDKOpenJ9PersonFilterOnly.txt 2>&1
echo "  5. PersonFilterAndGroup benchmarks :: less output/05_AdoptOpenJDKOpenJ9PersonFilterAndGroup.txt"
mvn -P AdoptOpenJDKOpenJ9,Person clean test exec:exec@filterAndGroup -t toolchains.xml > output/05_AdoptOpenJDKOpenJ9PersonFilterAndGroup.txt 2>&1
echo "  6. PersonIntSummaryStatistics benchmarks :: less output/05_AdoptOpenJDKOpenJ9PersonIntSummaryStats.txt"
mvn -P AdoptOpenJDKOpenJ9,Person clean test exec:exec@intSummaryStats -t toolchains.xml > output/05_AdoptOpenJDKOpenJ9PersonIntSummaryStats.txt 2>&1
echo "  7. PersonCombinedSummaryStatistics benchmarks :: less output/05_AdoptOpenJDKOpenJ9PersonCombinedSummaryStats.txt"
mvn -P AdoptOpenJDKOpenJ9,Person clean test exec:exec@combinedSummaryStats -t toolchains.xml > output/05_AdoptOpenJDKOpenJ9PersonCombinedSummaryStats.txt 2>&1
echo "Completed Adopt OpenJDK with OpenJ9 benchmarks."
echo "--------------------------------"
echo

