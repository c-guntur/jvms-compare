#!/bin/bash

# the benchmark-results directory has the JMH JSON data for each benchmark class
rm -rf benchmark-results

mkdir -p benchmark-results/int-list-filter
mkdir -p benchmark-results/int-list-sum
mkdir -p benchmark-results/int-list-transform
mkdir -p benchmark-results/person-combined-summary-stats
mkdir -p benchmark-results/person-filter-and-group
mkdir -p benchmark-results/person-filter-only
mkdir -p benchmark-results/person-int-summary-stats


# the outputs directory has the maven build outputs that include the tabulated benchmark data
rm -rf output

mkdir output
