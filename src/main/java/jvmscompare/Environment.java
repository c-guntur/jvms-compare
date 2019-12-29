package jvmscompare;

import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Environment {

    static final String BENCHMARK_SIZE = "benchmark.size";
//    static final String DEFAULT_SIZE = "1_000_000";
    static final String DEFAULT_SIZE = "100";
    public static int SIZE;


    static final String BENCHMARK_FORKS = "benchmark.forks";
    static final String DEFAULT_FORKS = "2";
    public static int FORKS;

    static final String BENCHMARK_WARMUPS = "benchmark.warmups";
    static final String DEFAULT_WARMUPS = "20";
    public static int WARMUP_ITERATIONS;

    static final String BENCHMARK_MEASUREMENTS = "benchmark.measurements";
    static final String DEFAULT_MEASUREMENTS = "10";
    public static int MEASUREMENT_ITERATIONS;

    static {
        try (InputStream input = Environment.class.getResourceAsStream("benchmark.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            SIZE = Integer.parseInt(
                    prop.getProperty(BENCHMARK_SIZE, DEFAULT_SIZE));

            FORKS = Integer.parseInt(
                    prop.getProperty(BENCHMARK_FORKS, DEFAULT_FORKS));

            WARMUP_ITERATIONS = Integer.parseInt(
                    prop.getProperty(BENCHMARK_WARMUPS, DEFAULT_WARMUPS));

            MEASUREMENT_ITERATIONS = Integer.parseInt(
                    prop.getProperty(BENCHMARK_MEASUREMENTS, DEFAULT_MEASUREMENTS));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static Options PARENT_OPTIONS = new OptionsBuilder()
            .forks(FORKS)
            .resultFormat(ResultFormatType.CSV)
            .warmupIterations(WARMUP_ITERATIONS)
            .measurementIterations(MEASUREMENT_ITERATIONS)
            .mode(Mode.Throughput)
            .timeUnit(TimeUnit.SECONDS).build();

}
