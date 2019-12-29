#!/bin/bash

. ./env.sh

echo "------------------------------------------ Testing Oracle JDK 11 ------------------------------------------"
${ORACLE_JDK_11_HOME}bin/java -version
echo

echo "------------------------------------------ Testing GraalVM EE 19.3 ------------------------------------------"
${GRAALVM_EE_19_HOME}bin/java -version
echo

echo "------------------------------------------ Testing GraalVM CE 19.3 ------------------------------------------"
${GRAALVM_CE_19_HOME}bin/java -version
echo

echo "------------------------------------------ Testing AdoptOpenJDK (Hotspot) 11 ------------------------------------------"
${ADOPT_HOTSPOT_11_HOME}bin/java -version
echo

echo "------------------------------------------ Testing AdoptOpenJDK (OpenJ9) 11 ------------------------------------------"
${ADOPT_OPENJ9_11_HOME}bin/java -version
echo

echo "------------------------------------------ Testing OpenJDK 11 ------------------------------------------"
${OPENJDK_11_HOME}bin/java -version
echo

