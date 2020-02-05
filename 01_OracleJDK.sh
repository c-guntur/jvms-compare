#!/bin/bash

source env.sh

echo "--------------------------------"
echo "01. Beginning Oracle JDK benchmarks ..."
export JAVA_HOME=${ORACLE_JDK_11_HOME}
export PATH=$JAVA_HOME/bin:$PATH

echo "JAVA_HOME=${JAVA_HOME}"
echo "Java Version"
echo "------------"
echo `java -version`
echo "------------"

export MAVEN_OPTS="-Xms1024m -Xmx1024m"

#IntList
echo "  1. IntListFilter benchmarks :: less output/01_OracleJDKIntListFilter.txt"
mvn -P OracleJDK,IntList clean test exec:exec -t toolchains.xml > output/01_OracleJDKIntListFilter.txt 2>&1
echo "         Completed at $(date "+%Y-%m-%d %H:%M:%S")"
echo "  2. IntListSum benchmarks :: less output/01_OracleJDKIntListSum.txt"
mvn -P OracleJDK,IntList clean test exec:exec@sum -t toolchains.xml > output/01_OracleJDKIntListSum.txt 2>&1
echo "         Completed at $(date "+%Y-%m-%d %H:%M:%S")"
echo "  3. IntListTransform benchmarks :: less output/01_OracleJDKIntListTransform.txt"
mvn -P OracleJDK,IntList clean test exec:exec@transform -t toolchains.xml > output/01_OracleJDKIntListTransform.txt 2>&1
echo "         Completed at $(date "+%Y-%m-%d %H:%M:%S")"
#Person
echo "  4. PersonFilter benchmarks :: less output/01_OracleJDKPersonFilterOnly.txt"
mvn -P OracleJDK,Person clean test exec:exec -t toolchains.xml > output/01_OracleJDKPersonFilterOnly.txt 2>&1
echo "         Completed at $(date "+%Y-%m-%d %H:%M:%S")"
echo "  5. PersonFilterAndGroup benchmarks :: less output/01_OracleJDKPersonFilterAndGroup.txt"
mvn -P OracleJDK,Person clean test exec:exec@filterAndGroup -t toolchains.xml > output/01_OracleJDKPersonFilterAndGroup.txt 2>&1
echo "         Completed at $(date "+%Y-%m-%d %H:%M:%S")"
echo "  6. PersonIntSummaryStatistics benchmarks :: less output/01_OracleJDKPersonIntSummaryStats.txt"
mvn -P OracleJDK,Person clean test exec:exec@intSummaryStats -t toolchains.xml > output/01_OracleJDKPersonIntSummaryStats.txt 2>&1
echo "         Completed at $(date "+%Y-%m-%d %H:%M:%S")"
echo "  7. PersonCombinedSummaryStatistics benchmarks :: less output/01_OracleJDKPersonCombinedSummaryStats.txt"
mvn -P OracleJDK,Person clean test exec:exec@combinedSummaryStats -t toolchains.xml > output/01_OracleJDKPersonCombinedSummaryStats.txt 2>&1
echo "         Completed at $(date "+%Y-%m-%d %H:%M:%S")"
echo "Completed Oracle JDK benchmarks."
echo "--------------------------------"
echo
