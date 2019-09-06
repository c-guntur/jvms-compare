package jvmscompare.benchmark;

import jvmscompare.JavaInformation;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.list.primitive.IntList;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.list.mutable.primitive.IntArrayList;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(2)
public class IntListFilter
{
    private List<Integer> jdkList;
    private IntList ecIntList;
    private MutableList<Integer> ecList;
    private ExecutorService executor;

    @Setup
    public void setUp()
    {
        this.executor = Executors.newWorkStealingPool();
        PrimitiveIterator.OfInt iterator = new Random(1L).ints(-1000, 1000).iterator();
        this.ecList = FastList.newWithNValues(1_000_000, iterator::nextInt);
        this.jdkList = new ArrayList<>(1_000_000);
        this.jdkList.addAll(this.ecList);
        this.ecIntList = this.ecList.collectInt(i -> i, new IntArrayList(1_000_000));
    }

    public static void main(String[] args) throws RunnerException
    {
        new JavaInformation().printJavaInformation();
        Options options = new OptionsBuilder().include(".*" + IntListFilter.class.getSimpleName() + ".*")
                .forks(2)
                .resultFormat(ResultFormatType.JSON)
                .result("benchmark-results/int-list-filter/" + args[0] + ".json")
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
    public List<Integer> filterJDKBoxed()
    {
        return this.jdkList.stream().filter(i -> i % 2 == 0).collect(Collectors.toList());
    }

    @Benchmark
    public List<Integer> filterJDKBoxedParallel()
    {
        return this.jdkList.parallelStream().filter(i -> i % 2 == 0).collect(Collectors.toList());
    }

    @Benchmark
    public IntList filterECPrimitive()
    {
        return this.ecIntList.select(i -> i % 2 == 0);
    }

    @Benchmark
    public MutableList<Integer> filterEC()
    {
        return this.ecList.select(i -> i % 2 == 0);
    }

    @Benchmark
    public MutableList<Integer> filterECParallel()
    {
        return this.ecList.asParallel(this.executor, 100_000).select(i -> i % 2 == 0).toList();
    }

}
