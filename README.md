# JVMs Compare
## JVM comparisons - using benchmarks

This repository hosts a few unit tests run as a maven project with different profiles.

The unit tests rely on [**Eclipse Collections**](https://eclipse.org/collections) library and 
use JMH for benchmarks.

Various profiles are intended to execute the same benchmarks on different JVMs, such as 
GraalVM CE, GraalVM EE, OpenJDK 11, Oracle JDK 8.

## What is currently tested?

Sequence | JDK | Version |  Notes
-------------- | ------------------ | ---------------------- | -------------------------------
01 | Oracle JDK 11 | 11.0.5 | 
02 | Graal VM EE | 19.3.0.2 | 
03 | Graal VM CE | 19.3.0.2 | 
04 | AdoptOpenJDK 11 w/Hotspot | 11.0.5 +10 | 
05 | AdoptOpenJDK 11 w/OpenJ9 | 11.0.5 +10 | OpenJ9 build openj9-0.17.0
06 | OpenJDK 11 | 11.0.2 | 
07 | OpenJDK 11 w/Graal | 11.0.2 | Experimental Option +UseJVMCICompiler
08 | GraalEE w/C2 | 19.3.0.2 | Experimental Option -UseJVMCICompiler

## Links to download the JDKs
1. Oracle JDK 11.0.5: https://www.oracle.com/technetwork/java/javase/downloads/jdk11-downloads-5066655.html
1. GraalVM CE 19.3.0.2: https://github.com/graalvm/graalvm-ce-builds/releases
1. GraalVM EE 19.3.0.2: https://www.oracle.com/downloads/graalvm-downloads.html
1. AdoptOpenJDK Hotspot 11.0.5 +10: https://adoptopenjdk.net/?variant=openjdk11&jvmVariant=hotspot
1. AdoptOpenJDK OpenJ9 11.0.5 +10: https://adoptopenjdk.net/?variant=openjdk11&jvmVariant=openj9 
1. OpenJDK 11.0.2: https://jdk.java.net/archive/

#### NOTE: OS X Catalina Security - trusted executables.
It is important to test each downloaded JDK before running the benchmarks, since some of the distrubutors 
are not yet endorsed. TODO: create a script to test JDKs.

Meanwhile, run a `java -version` by navigating to the HOME directory of each JDK from a terminal. 
If any JDK throws a security constraint, the quick work-around is to remove the quarantine attribute:
For instance for GraalVM EE 19.3.0.2:

`xattr -d com.apple.quarantine /Library/Java/JavaVirtualMachines/graalvm-ee-java11-19.3.0.2/`

## Setup Steps

1. Setup an IDE if you wish to include more Benchmarks or edit configurations.
1. Setup the JDKs that are being tested.
1. Setup Apache Maven (v3.6.1 or above).
1. Setup the environment variables (at a minimum, GRAALVM_EE_HOME and ORACLE_JAVA8_HOME).


### Samples:

  #### Setting GRAALVM_CE_HOME
    `export GRAALVM_CE_HOME=/Library/Java/JavaVirtualMachines/graalvm-ce-19.2.0/Contents/Home/`

#### Setting GRAALVM_EE_HOME
    `export GRAALVM_EE_HOME=/Library/Java/JavaVirtualMachines/graalvm-ee-19.2.0/Contents/Home/`

#### Setting ORACLE_JAVA8_HOME
    `export ORACLE_JAVA8_HOME=/Library/Java/JavaVirtualMachines/oraclejdk1.8.0_212.jdk/Contents/Home/`

## Usage

Different benchmark tests can be run using different profiles set up in the `pom.xml`. More profiles
can be created by:

1. copy/pasting an existing profile in the `pom.xml`.
1. renaming the profile _**appropriately**, to reflect the toolchain and type of benchmark_.
1. modifying the toolchain configuration in **maven-toolchains-plugin** to pick the right toolchain.
1. {optionally} updating the **exec-maven-plugin** to run the appropriate Benchmark class.

### Default maven invocation
The maven command to mvn -P **<profile name>**  clean test exec:exec -t toolchains.xml

#### Run Oracle Java 8 with Primitive IntList Filter Benchmarks
    export MAVEN_OPTS=
    mvn -P OracleJDK8,IntList clean test exec:exec -t toolchains.xml 

#### Run Oracle Java 8 with Primitive IntList Sum Benchmarks (see IntList profile in [pom.xml](pom.xml))
    export MAVEN_OPTS=
    mvn -P OracleJDK8,IntList clean test exec:exec@sum -t toolchains.xml 

#### Run Oracle Java 8 with Person IntSummaryStatistics Benchmarks
    export MAVEN_OPTS=
    mvn -P OracleJDK8,Person clean test exec:exec@intSummaryStats -t toolchains.xml

#### Run Graal VM EE with Primitive IntList Filter Benchmarks
    export MAVEN_OPTS=
    mvn -P GraalEE,IntList clean test exec:exec -t toolchains.xml 

#### Run Graal VM EE with Primitive IntList Sum Benchmarks (see IntList profile in [pom.xml](pom.xml))
    export MAVEN_OPTS=
    mvn -P GraalEE,IntList clean test exec:exec@sum -t toolchains.xml 

#### Run Graal VM EE with Person IntSummaryStatistics Benchmarks
    export MAVEN_OPTS=
    mvn -P GraalEE,Person clean test exec:exec@intSummaryStats -t toolchains.xml

#### Run Graal VM EE using C2 Compiler with Primitive IntList Filter Benchmarks
    export MAVEN_OPTS=
    mvn -P GraalEEHotspot clean test exec:exec -t toolchains.xml 

#### Run Graal VM EE using C2 Compiler with Person IntSummary Statistics Benchmarks
    export MAVEN_OPTS="-XX:+UnlockExperimentalVMOptions -XX:-UseJVMCICompiler"
    mvn -P GraalEEHotspot clean test exec:exec@intSummaryStats -t toolchains.xml 

## Notes on toolchains

As of Apache Maven 3.6.1, there is a bug that prevents using an environment variable in the 
`toolchains.xml`. This means that toolchains.xml will be different on different machines. 

Update the `toolchains.xml` locally and point to:
 
* the location of the toolchain's JDK

Additional toolchains can also be added if more JDK distros are being tested.

## Run ALL Benchmarks

There is a root run-all.sh that runas all 8 JDKs against the 7 benchmark classes. 

**This shell script deletes all existing results**

You can also run individual shell scripts that a re prefixed with a number. These numbered 
shell scripts are per JDK/JVM being tested.

In order to delete existing outputs, use the below:

### Delete all maven logs

From the root folder, run:

```
find ./output/ -maxdepth 3 -type f -name "*.txt" -delete
```

### Delete all benchmark outputs in JSON

From the root folder, run:

```
find ./benchmark-results/ -maxdepth 3 -type f -name "*.json" -delete

``` 

### Delete all outputs and reset to empty

From the root folder, run:

```
./setup.sh

```


### Experimenting with Results
If you wish to experiment with the results of the benchmarks we have already run you can look at the folders:
`results_for_1k_size` and `results_for_1M_size`.
Notes about the results:
* The results for each benchmark run is saved in it's own respective folder.
* The results are saved as json files as well as maven build output.
* For json files navigate to benchmark-results (For Eg. /results_for_1k_size/benchmark-results/int-lists-filter)
* For maven build output navigate to output (For Eg. /results_for_1k_size/output/)
