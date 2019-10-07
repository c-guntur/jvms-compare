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

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(2)
public class PersonFilterAndGroup
{
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newWorkStealingPool();

    public static void main(String[] args) throws RunnerException
    {
        new JavaInformation().printJavaInformation();
        Options options = new OptionsBuilder().include(".*" + PersonFilterAndGroup.class.getSimpleName() + ".*")
                .forks(2)
                .resultFormat(ResultFormatType.JSON)
                .result("benchmark-results/person-filter-and-group/" + args[0] + ".json")
                .warmupIterations(20)
                .measurementIterations(10)
                .mode(Mode.Throughput)
                .timeUnit(TimeUnit.SECONDS)
                .build();
        new Runner(options).run();
    }

    @Benchmark
    public Map<Integer, List<Person>> filterAndGroupByAgeJDK_parallel()
    {
        Map<Integer, List<Person>> grouped =
                Person.getJDKPeople().parallelStream()
                        .filter(person -> person.getHeightInInches() < 150)
                        .collect(Collectors.groupingBy(Person::getAge));
        return grouped;
    }

    @Benchmark
    public ListMultimap<Integer, Person> filterAndGroupByAgeECLazy_parallel()
    {
        ListMultimap<Integer, Person> grouped =
                Person.getECPeople().asParallel(EXECUTOR_SERVICE, 100_000)
                        .select(person -> person.getHeightInInches() < 150)
                        .groupBy(Person::getAge);
        return grouped;
    }

    @Benchmark
    public MutableMultimap<Integer, Person> filterAndGroupByAgeECEager_parallel()
    {
        Iterable<Person> select =
                ParallelIterate.select(Person.getECPeople(), person -> person.getHeightInInches() < 150);
        MutableMultimap<Integer, Person> grouped =
                ParallelIterate.groupBy(select, Person::getAge);
        return grouped;
    }

    @Benchmark
    public MutableListMultimap<Integer, Person> filterAndGroupByAgeECStream_parallel()
    {
        MutableListMultimap<Integer, Person> grouped =
                Person.getJDKPeople().parallelStream()
                        .filter(person -> person.getHeightInInches() < 150)
                        .collect(Collectors2.toListMultimap(Person::getAge));
        return grouped;
    }

    @Benchmark
    public Map<Integer, List<Person>> filterAndGroupByAgeJDK_serial()
    {
        Map<Integer, List<Person>> grouped =
                Person.getJDKPeople().stream()
                        .filter(person -> person.getHeightInInches() < 150)
                        .collect(Collectors.groupingBy(Person::getAge));
        return grouped;
    }

    @Benchmark
    public MutableListMultimap<Integer, Person> filterAndGroupByAgeECEager_serial()
    {
        MutableListMultimap<Integer, Person> grouped =
                Person.getECPeople()
                        .select(person -> person.getHeightInInches() < 150)
                        .groupBy(Person::getAge);
        return grouped;
    }

    @Benchmark
    public Multimap<Integer, Person> filterAndGroupByAgeECLazy_serial()
    {
        Multimap<Integer, Person> grouped = Person.getECPeople()
                .asLazy()
                .select(person -> person.getHeightInInches() < 150)
                .groupBy(Person::getAge);
        return grouped;
    }

}
