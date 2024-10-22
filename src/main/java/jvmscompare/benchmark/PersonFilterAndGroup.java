package jvmscompare.benchmark;

import jvmscompare.JavaInformation;
import jvmscompare.Person;
import org.eclipse.collections.api.multimap.Multimap;
import org.eclipse.collections.api.multimap.MutableMultimap;
import org.eclipse.collections.api.multimap.list.ListMultimap;
import org.eclipse.collections.api.multimap.list.MutableListMultimap;
import org.eclipse.collections.impl.collector.Collectors2;
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
public class PersonFilterAndGroup
{
    private static final String BENCHMARK_INCLUSION_REGEXP = ".*" + PersonFilterAndGroup.class.getSimpleName() + ".*";
    private static final String BENCHMARK_RESULTS_DIRECTORY = "benchmark-results/person-filter-and-group/";

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newWorkStealingPool();
    private static final int SIX_FEET = 72;

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
    public Map<Integer, List<Person>> filterAndGroupByAge_JDK_Stream_Serial()
    {
        Map<Integer, List<Person>> grouped =
                Person.getJDKPeople().stream()
                        .filter(person -> person.getHeightInInches() < SIX_FEET)
                        .collect(Collectors.groupingBy(Person::getAge));
        return grouped;
    }

    @Benchmark
    public Multimap<Integer, Person> filterAndGroupByAge_EC_Lazy_Serial()
    {
        Multimap<Integer, Person> grouped = Person.getECPeople()
                .asLazy()
                .select(person -> person.getHeightInInches() < SIX_FEET)
                .groupBy(Person::getAge);
        return grouped;
    }

    @Benchmark
    public MutableListMultimap<Integer, Person> filterAndGroupByAge_EC_Eager_Serial()
    {
        MutableListMultimap<Integer, Person> grouped =
                Person.getECPeople()
                        .select(person -> person.getHeightInInches() < SIX_FEET)
                        .groupBy(Person::getAge);
        return grouped;
    }

    @Benchmark
    public Map<Integer, List<Person>> filterAndGroupByAge_JDK_Stream_Parallel()
    {
        Map<Integer, List<Person>> grouped =
                Person.getJDKPeople().parallelStream()
                        .filter(person -> person.getHeightInInches() < SIX_FEET)
                        .collect(Collectors.groupingBy(Person::getAge));
        return grouped;
    }

    @Benchmark
    public MutableListMultimap<Integer, Person> filterAndGroupByAge_EC_Stream_Parallel()
    {
        MutableListMultimap<Integer, Person> grouped =
                Person.getJDKPeople().parallelStream()
                        .filter(person -> person.getHeightInInches() < SIX_FEET)
                        .collect(Collectors2.toListMultimap(Person::getAge));
        return grouped;
    }

    @Benchmark
    public ListMultimap<Integer, Person> filterAndGroupByAge_EC_Lazy_Parallel()
    {
        ListMultimap<Integer, Person> grouped =
                Person.getECPeople().asParallel(EXECUTOR_SERVICE, 100_000)
                        .select(person -> person.getHeightInInches() < SIX_FEET)
                        .groupBy(Person::getAge);
        return grouped;
    }

    @Benchmark
    public MutableMultimap<Integer, Person> filterAndGroupByAge_EC_Eager_Parallel()
    {
        Iterable<Person> select =
                ParallelIterate.select(
                        Person.getECPeople(),
                        person -> person.getHeightInInches() < SIX_FEET);

        MutableMultimap<Integer, Person> grouped =
                ParallelIterate.groupBy(select, Person::getAge);
        return grouped;
    }

}
