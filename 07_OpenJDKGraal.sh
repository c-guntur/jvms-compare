#!/bin/bash

source env.sh

echo "--------------------------------"
echo "07. Beginning OpenJDK (with JVMCI Compiler) benchmarks ..."
export JAVA_HOME=${OPENJDK_11_HOME}
export PATH=$JAVA_HOME/bin:$PATH
export MAVEN_OPTS=

echo "JAVA_HOME=${JAVA_HOME}"
echo "Java Version"
echo "------------"
echo `java -version`
echo "------------"

export MAVEN_OPTS="-XX:+UnlockExperimentalVMOptions -XX:+EnableJVMCI -XX:+UseJVMCICompiler"

#IntList
echo "  1. IntListFilter benchmarks :: less output/07_OpenJDKGraalIntListFilter.txt"
mvn -P OpenJDKGraal clean test exec:exec -t toolchains.xml > output/07_OpenJDKGraalIntListFilter.txt 2>&1
echo "         Completed at ${TIMESTAMP}"
echo "  2. IntListSum benchmarks :: less output/07_OpenJDKGraalIntListSum.txt"
mvn -P OpenJDKGraal clean test exec:exec@sum -t toolchains.xml > output/07_OpenJDKGraalIntListSum.txt 2>&1
echo "         Completed at ${TIMESTAMP}"
echo "  3. IntListTransform benchmarks :: less output/07_OpenJDKGraalIntListTransform.txt"
mvn -P OpenJDKGraal clean test exec:exec@transform -t toolchains.xml > output/07_OpenJDKGraalIntListTransform.txt 2>&1
echo "         Completed at ${TIMESTAMP}"
#Person
echo "  4. PersonFilter benchmarks :: less "
mvn -P OpenJDKGraal clean test exec:exec@filterOnly -t toolchains.xml > output/07_OpenJDKGraalPersonFilterOnly.txt 2>&1
echo "         Completed at ${TIMESTAMP}"
echo "  5. PersonFilterAndGroup benchmarks :: less output/07_OpenJDKGraalPersonFilterOnly.txt"
mvn -P OpenJDKGraal clean test exec:exec@filterAndGroup -t toolchains.xml > output/07_OpenJDKGraalPersonFilterAndGroup.txt 2>&1
echo "         Completed at ${TIMESTAMP}"
echo "  6. PersonIntSummaryStatistics benchmarks :: less output/07_OpenJDKGraalPersonIntSummaryStats.txt"
mvn -P OpenJDKGraal clean test exec:exec@intSummaryStats -t toolchains.xml > output/07_OpenJDKGraalPersonIntSummaryStats.txt 2>&1
echo "         Completed at ${TIMESTAMP}"
echo "  7. PersonCombinedSummaryStatistics benchmarks :: less output/07_OpenJDKGraalPersonCombinedSummaryStats.txt"
mvn -P OpenJDKGraal clean test exec:exec@combinedSummaryStats -t toolchains.xml > output/07_OpenJDKGraalPersonCombinedSummaryStats.txt 2>&1
echo "         Completed at ${TIMESTAMP}"
echo "Completed OpenJDK (with JVMCI Compiler) benchmarks."
echo "--------------------------------"
echo

