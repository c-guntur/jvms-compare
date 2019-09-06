package jvmscompare.benchmark;

import jvmscompare.JavaInformation;
import jvmscompare.Person;
import org.eclipse.collections.impl.collector.Collectors2;
import org.eclipse.collections.impl.factory.Lists;
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

import java.util.Collection;
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
public class PersonFilterOnly
{
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newWorkStealingPool();

    public static void main(String[] args) throws RunnerException
    {
        new JavaInformation().printJavaInformation();
        Options options = new OptionsBuilder().include(".*" + PersonFilterOnly.class.getSimpleName() + ".*")
                .forks(2)
                .resultFormat(ResultFormatType.JSON)
                .result("benchmark-results/person-filter-only/" + args[0] + ".json")
                .warmupIterations(10)
                .warmupTime(TimeValue.seconds(5L))
                .measurementIterations(10)
                .measurementTime(TimeValue.seconds(5L))
                .timeout(TimeValue.seconds(20))
                .mode(Mode.Throughput)
                .timeUnit(TimeUnit.SECONDS)
                .build();
        new Runner(options).run();
    }

    @Benchmark
    public List<Person> filterJDK_parallel()
    {
        List<Person> filtered =
                Person.getJDKPeople().parallelStream()
                        .filter(person -> person.getHeightInInches() < 150)
                        .filter(person -> person.getHeightInInches() > 80)
                        .collect(Collectors.toList());
        return filtered;
    }

    @Benchmark
    public List<Person> filterECLazy_parallel()
    {
        List<Person> filtered =
                Person.getECPeople().asParallel(EXECUTOR_SERVICE, 100_000)
                        .select(person -> person.getHeightInInches() < 150)
                        .select(person -> person.getHeightInInches() > 80)
                        .toList();
        return filtered;
    }

    @Benchmark
    public Collection<Person> filterECEager_parallel()
    {
        Collection<Person> select =
                ParallelIterate.select(Person.getECPeople(), person -> person.getHeightInInches() < 150);
        return ParallelIterate.select(select, person -> person.getHeightInInches() > 80);
    }

    @Benchmark
    public List<Person> filterECStream_parallel()
    {
        List<Person> filtered =
                Person.getJDKPeople().parallelStream()
                        .filter(person -> person.getHeightInInches() < 150)
                        .collect(Collectors2.select(person -> person.getHeightInInches() > 80, Lists.mutable::empty));
        return filtered;
    }

    @Benchmark
    public Map<Integer, List<Person>> filterJDK_serial()
    {
        Map<Integer, List<Person>> grouped =
                Person.getJDKPeople().stream()
                        .filter(person -> person.getHeightInInches() < 150)
                        .filter(person -> person.getHeightInInches() > 80)
                        .collect(Collectors.groupingBy(Person::getAge));
        return grouped;
    }

    @Benchmark
    public List<Person> filterECEager_serial()
    {
        List<Person>  filtered = Person.getECPeople()
                .select(person -> person.getHeightInInches() < 150)
                .select(person -> person.getHeightInInches() > 80);
        return filtered;
    }

    @Benchmark
    public List<Person> filterECLazy_serial()
    {
        List<Person>  filtered = Person.getECPeople()
                .asLazy()
                .select(person -> person.getHeightInInches() < 150)
                .select(person -> person.getHeightInInches() > 80)
                .toList();
        return filtered;
    }

}
