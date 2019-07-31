# JVMs Compare
## JVM comparisons - using benchmarks

This repository hosts a few unit tests run as a maven project with different profiles.

The unit tests rely on [**Eclipse Collections**](https://eclipse.org/collections) library and use JMH for benchmarks.

Various profiles are intended to execute the same benchmarks on different JVMs, such as GraalVM CE, GraalVM EE, OpenJDK 8, Oracle JDK 8.

## Setup Steps

Setup the environment variables for GRAALVM_HOME and JAVA_HOME etc.

~~### Samples:

#### Setting GRAALVM_HOME
`export GRAALVM_HOME=/Library/Java/JavaVirtualMachines/graalvm-ee-1.0.0-rc13/Contents/Home/`

#### Setting JAVA8_HOME
`export JAVA8_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_212.jdk/Contents/Home/`

~~## Execute Maven~~

~~### Graal~~

~~The command line to run this project with GraalVM:~~

~~`mvn clean install -P graalee -t toolchains.xml`~~

~~### Oracle Java 8~~

~~`mvn clean install -P oracleJava8 -t toolchains.xml`~~

## Usage

mvn -P graalprimitive clean test exec:exec -t toolchains.xml 

mvn -P oracleprimitive clean test exec:exec -t toolchains.xml 

mvn -P graalobject clean test exec:exec -t toolchains.xml 

mvn -P oracleobject clean test exec:exec -t toolchains.xml 

