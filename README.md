# JVMs Compare
## JVM comparisons - using benchmarks

This repository hosts a few unit tests run as a maven project with different profiles.

The unit tests rely on [**Eclipse Collections**](https://eclipse.org/collections) library and 
use JMH for benchmarks.

Various profiles are intended to execute the same benchmarks on different JVMs, such as 
GraalVM CE, GraalVM EE, OpenJDK 8, Oracle JDK 8.

## Setup Steps

1. Setup an IDE if you wish to include more Benchmarks or edit configurations.
1. Setup the JDKs that are being tested.
1. Setup Apache Maven (v3.6.1 or above).
1. Setup the environment variables (at a minimum, GRAALVM_EE_HOME and ORACLE_JAVA8_HOME).


### Samples:

  #### Setting GRAALVM_CE_HOME
    `export GRAALVM_CE_HOME=/Library/Java/JavaVirtualMachines/graalvm-ce-1.0.0-rc13/Contents/Home/`

#### Setting GRAALVM_EE_HOME
    `export GRAALVM_EE_HOME=/Library/Java/JavaVirtualMachines/graalvm-ee-1.0.0-rc13/Contents/Home/`

#### Setting ORACLE_JAVA8_HOME
    `export ORACLE_JAVA8_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_212.jdk/Contents/Home/`

## Usage

Different benchmark tests can be run using different profiles set up in the `pom.xml`. More profiles
can be created by:

1. copy/pasting an existing profile in the `pom.xml`.
1. renaming the profile _**appropriately**, to reflect the toolchain and type of benchmark_.
1. modifying the toolchain configuration in **maven-toolchains-plugin** to pick the right toolchain.
1. updating the **exec-maven-plugin** to run the appropriate Benchmark class.

### Default maven invocation
The maven command to mvn -P **<profile name>**  clean test exec:exec -t toolchains.xml

#### Run Graal VM EE with Primitive IntList Benchmarks
    `mvn -P GraalEEIntList clean test exec:exec -t toolchains.xml` 

#### Run Oracle Java 8 with Primitive IntList Benchmarks
    `mvn -P OracleJava8IntList clean test exec:exec -t toolchains.xml` 

#### Run Graal VM EE with Object Benchmarks
    `mvn -P GraalEEPerson clean test exec:exec -t toolchains.xml` 

#### Run Oracle Java 8 with Object Benchmarks
    `mvn -P OracleJava8Person clean test exec:exec -t toolchains.xml`

## Notes on toolchains

As of Apache Maven 3.6.1, there is a bug that prevents using an environment variable in the 
`toolchains.xml`. This means that toolchains.xml will be different on different machines. 

Update the `toolchains.xml` locally and point to:
 
* the location of a GraalVM HOME at line 59.
* the location of an Oracle Java 8 HOME at line 70.

Additional toolchains can also be added if more JDK distros are being tested.

## How to run embedded VMs

### Running the embedded GraalVM in Oracle JDK 8

Two steps are needed to run the GraalVM CE embedded in Oracle Java 8

1. Export the maven options:
   export MAVEN_OPTS="-XX:+UnlockExperimentalVMOptions -XX:+EnableJVMCI -XX:+UseJVMCICompiler"
2. Run the OracleJava8 profiles (either one below)
   * `mvn -P OracleJava8Person clean test exec:exec -t toolchains.xml`
   * `mvn -P OracleJava8IntList clean test exec:exec -t toolchains.xml` 
   
### Running the embedded Hotspot in GraalVM EE 19.1.1

???