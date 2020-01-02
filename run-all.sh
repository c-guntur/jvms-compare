#!/bin/bash

./env.sh && ./setup.sh && ./01_OracleJDK.sh && sleep 10 && ./02_GraalEE.sh && sleep 10 && ./03_GraalCE.sh && sleep 10 && ./04_AdoptOpenJDKHotspot.sh && sleep 10 && ./05_AdoptOpenJDKOpenJ9.sh && sleep 10 && ./06_OpenJDKHotspot.sh && sleep 10 && ./07_OpenJDKGraal.sh && sleep 10 && ./08_GraalEEHotspot.sh && sleep 10
