package jvmscompare.benchmark;

import jvmscompare.JavaInformation;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.list.primitive.IntList;
import org.eclipse.collections.impl.factory.primitive.IntLists;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.list.mutable.primitive.IntArrayList;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static jvmscompare.Environment.PARENT_OPTIONS;
import static jvmscompare.Environment.SIZE;

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
public class IntListFilter
{
    private static final String BENCHMARK_INCLUSION_REGEXP = ".*" + IntListFilter.class.getSimpleName() + ".*";
    private static final String BENCHMARK_RESULTS_DIRECTORY = "benchmark-results/int-list-filter/";

    private List<Integer> jdkList;
    private IntList ecIntList;
    private MutableList<Integer> ecList;
    private ExecutorService executor;

    @Setup
    public void setUp()
    {
        this.executor = Executors.newWorkStealingPool();
        PrimitiveIterator.OfInt iterator = new Random(1L).ints(-1000, 1000).iterator();
        this.ecList = FastList.newWithNValues(SIZE, iterator::nextInt);
        this.jdkList = new ArrayList<>(SIZE);
        this.jdkList.addAll(this.ecList);
        this.ecIntList = this.ecList.collectInt(i -> i, new IntArrayList(SIZE));
    }

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

    @Benchmark
    public IntList filterECPrimitiveParallelStream()
    {
        return IntLists.mutable.withAll(
                this.ecIntList.primitiveParallelStream()
                        .filter(i -> i % 2 == 0));
    }

}
