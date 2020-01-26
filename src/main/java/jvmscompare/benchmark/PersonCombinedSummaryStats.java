package jvmscompare.benchmark;

import jvmscompare.JavaInformation;
import jvmscompare.Person;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.DoubleSummaryStatistics;
import java.util.IntSummaryStatistics;
import java.util.concurrent.TimeUnit;

import static jvmscompare.Environment.PARENT_OPTIONS;

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
public class PersonCombinedSummaryStats
{
    private static final String BENCHMARK_INCLUSION_REGEXP = ".*" + PersonCombinedSummaryStats.class.getSimpleName() + ".*";
    private static final String BENCHMARK_RESULTS_DIRECTORY = "benchmark-results/person-combined-summary-stats/";

    public static void main(String[] args) throws RunnerException
    {
        new JavaInformation().printJavaInformation();

        Options options = new OptionsBuilder().parent(PARENT_OPTIONS)
                .include(BENCHMARK_INCLUSION_REGEXP)
                .result(BENCHMARK_RESULTS_DIRECTORY + args[0] + ".csv")
                .build();

        new Runner(options).run();
    }

    @Benchmark
    public Object[] combinedStatistics_JDK_Stream_Serial()
    {
        DoubleSummaryStatistics stats1 =
                Person.getJDKPeople().stream().mapToDouble(Person::getHeightInInches).summaryStatistics();
        DoubleSummaryStatistics stats2 =
                Person.getJDKPeople().stream().mapToDouble(Person::getWeightInPounds).summaryStatistics();
        IntSummaryStatistics stats3 =
                Person.getJDKPeople().stream().mapToInt(Person::getAge).summaryStatistics();
        return new Object[]{stats1, stats2, stats3};
    }

    @Benchmark
    public Object[] combinedStatistics_EC_Lazy_Serial()
    {
        DoubleSummaryStatistics stats1 =
                Person.getECPeople().asLazy().collectDouble(Person::getHeightInInches).summaryStatistics();
        DoubleSummaryStatistics stats2 =
                Person.getECPeople().asLazy().collectDouble(Person::getWeightInPounds).summaryStatistics();
        IntSummaryStatistics stats3 =
                Person.getECPeople().asLazy().collectInt(Person::getAge).summaryStatistics();
        return new Object[]{stats1, stats2, stats3};
    }

    @Benchmark
    public Object[] combinedStatistics_EC_Eager_Serial()
    {
        DoubleSummaryStatistics stats1 =
                Person.getECPeople().summarizeDouble(Person::getHeightInInches);
        DoubleSummaryStatistics stats2 =
                Person.getECPeople().summarizeDouble(Person::getWeightInPounds);
        IntSummaryStatistics stats3 =
                Person.getECPeople().summarizeInt(Person::getAge);
        return new Object[]{stats1, stats2, stats3};
    }

    @Benchmark
    public Object[] combinedStatistics_JDK_Stream_Parallel()
    {
        DoubleSummaryStatistics stats1 =
                Person.getJDKPeople().parallelStream().mapToDouble(Person::getHeightInInches).summaryStatistics();
        DoubleSummaryStatistics stats2 =
                Person.getJDKPeople().parallelStream().mapToDouble(Person::getWeightInPounds).summaryStatistics();
        IntSummaryStatistics stats3 =
                Person.getJDKPeople().parallelStream().mapToInt(Person::getAge).summaryStatistics();
        return new Object[]{stats1, stats2, stats3};
    }

    @Benchmark
    public Object[] combinedStatistics_EC_Stream_Parallel()
    {
        DoubleSummaryStatistics stats1 =
                Person.getECPeople().parallelStream().mapToDouble(Person::getHeightInInches).summaryStatistics();
        DoubleSummaryStatistics stats2 =
                Person.getECPeople().parallelStream().mapToDouble(Person::getWeightInPounds).summaryStatistics();
        IntSummaryStatistics stats3 =
                Person.getECPeople().parallelStream().mapToInt(Person::getAge).summaryStatistics();
        return new Object[]{stats1, stats2, stats3};
    }

}
