package jvmscompare.benchmark;

import jvmscompare.JavaInformation;
import jvmscompare.Person;
import org.eclipse.collections.impl.collector.Collectors2;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.parallel.ParallelIterate;
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

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static jvmscompare.Environment.PARENT_OPTIONS;

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
public class PersonFilterOnly
{
    private static final String BENCHMARK_INCLUSION_REGEXP = ".*" + PersonFilterOnly.class.getSimpleName() + ".*";
    private static final String BENCHMARK_RESULTS_DIRECTORY = "benchmark-results/person-filter-only/";

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newWorkStealingPool();

    private static final int SIX_FEET = 72;
    private static final int FIVE_FEET = 60;

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
    public Map<Integer, List<Person>> filter_JDK_Stream_Serial()
    {
        Map<Integer, List<Person>> grouped =
                Person.getJDKPeople().stream()
                        .filter(person -> person.getHeightInInches() < SIX_FEET)
                        .filter(person -> person.getHeightInInches() > FIVE_FEET)
                        .collect(Collectors.groupingBy(Person::getAge));
        return grouped;
    }

    @Benchmark
    public List<Person> filter_EC_Lazy_Serial()
    {
        List<Person>  filtered = Person.getECPeople()
                .asLazy()
                .select(person -> person.getHeightInInches() < SIX_FEET)
                .select(person -> person.getHeightInInches() > FIVE_FEET)
                .toList();
        return filtered;
    }

    @Benchmark
    public List<Person> filter_EC_Eager_Serial()
    {
        List<Person>  filtered = Person.getECPeople()
                .select(person -> person.getHeightInInches() < SIX_FEET)
                .select(person -> person.getHeightInInches() > FIVE_FEET);
        return filtered;
    }

    @Benchmark
    public List<Person> filter_JDK_Stream_Parallel()
    {
        List<Person> filtered =
                Person.getJDKPeople().parallelStream()
                        .filter(person -> person.getHeightInInches() < SIX_FEET)
                        .filter(person -> person.getHeightInInches() > FIVE_FEET)
                        .collect(Collectors.toList());
        return filtered;
    }

    @Benchmark
    public List<Person> filter_EC_Stream_Parallel()
    {
        List<Person> filtered =
                Person.getJDKPeople().parallelStream()
                        .filter(person -> person.getHeightInInches() < SIX_FEET)
                        .collect(Collectors2.select(
                                person -> person.getHeightInInches() > FIVE_FEET,
                                Lists.mutable::empty));
        return filtered;
    }

    @Benchmark
    public List<Person> filter_EC_Lazy_Parallel()
    {
        List<Person> filtered =
                Person.getECPeople().asParallel(EXECUTOR_SERVICE, 100_000)
                        .select(person -> person.getHeightInInches() < SIX_FEET)
                        .select(person -> person.getHeightInInches() > FIVE_FEET)
                        .toList();
        return filtered;
    }

    @Benchmark
    public Collection<Person> filter_EC_Eager_Parallel()
    {
        Collection<Person> select =
                ParallelIterate.select(
                        Person.getECPeople(),
                        person -> person.getHeightInInches() < SIX_FEET);
        return ParallelIterate.select(
                select,
                person -> person.getHeightInInches() > FIVE_FEET);
    }

}
