package jvmscompare.benchmark;

import java.util.IntSummaryStatistics;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import jvmscompare.JavaInformation;
import jvmscompare.Person;
import org.eclipse.collections.api.set.primitive.IntSet;
import org.eclipse.collections.impl.collector.Collectors2;
import org.eclipse.collections.impl.factory.primitive.IntSets;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import static jvmscompare.Environment.PARENT_OPTIONS;

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
public class PersonIntSummaryStats
{
    private static final String BENCHMARK_INCLUSION_REGEXP = ".*" + PersonIntSummaryStats.class.getSimpleName() + ".*";
    private static final String BENCHMARK_RESULTS_DIRECTORY = "benchmark-results/person-int-summary-stats/";

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
    public IntSummaryStatistics intSummaryStatistics_JDK_Stream_Serial()
    {
        Set<Integer> uniqueAges =
                Person.getJDKPeople().stream()
                        .mapToInt(Person::getAge)
                        .boxed()
                        .collect(Collectors.toSet());
        IntSummaryStatistics summary = uniqueAges.stream().mapToInt(i -> i).summaryStatistics();
        return summary;
    }

    @Benchmark
    public IntSummaryStatistics intSummaryStatistics_EC_Lazy_Serial()
    {
        IntSet uniqueAges =
                Person.getECPeople().asLazy().collectInt(Person::getAge).toSet();
        IntSummaryStatistics summary = uniqueAges.summaryStatistics();
        return summary;
    }

    @Benchmark
    public IntSummaryStatistics intSummaryStatistics_EC_Eager_Serial()
    {
        IntSet uniqueAges =
                Person.getECPeople().collectInt(Person::getAge, IntSets.mutable.empty());
        IntSummaryStatistics summary = uniqueAges.summaryStatistics();
        return summary;
    }

    @Benchmark
    public IntSummaryStatistics intSummaryStatistics_JDK_Stream_Parallel()
    {
        Set<Integer> uniqueAges =
                Person.getJDKPeople().parallelStream()
                        .mapToInt(Person::getAge)
                        .boxed()
                        .collect(Collectors.toSet());
        IntSummaryStatistics summary = uniqueAges.parallelStream().mapToInt(i -> i).summaryStatistics();
        return summary;
    }

    @Benchmark
    public IntSummaryStatistics intSummaryStatistics_EC_Stream_Parallel()
    {
        IntSet uniqueAges =
                Person.getECPeople()
                        .parallelStream()
                        .collect(Collectors2.collectInt(Person::getAge, IntSets.mutable::empty));
        IntSummaryStatistics summary = uniqueAges.summaryStatistics();
        return summary;
    }
}
