#!/bin/bash

find ./benchmark-results/ -maxdepth 3 -type f -name "*.json" -delete

find ./output/ -maxdepth 3 -type f -name "*.txt" -delete

./01_OracleJDK8.sh && sleep 10 && ./02_GraalEE.sh && sleep 10 && ./03_GraalCE.sh && sleep 10 && ./04_AdoptOpenJDKHotspot.sh && sleep 10 && ./05_AdoptOpenJDKOpenJ9.sh && sleep 10 && ./06_OpenJDK11Hotspot.sh && sleep 10 && ./07_OpenJDK11Graal.sh && sleep 10 && ./08_GraalEEHotspot.sh