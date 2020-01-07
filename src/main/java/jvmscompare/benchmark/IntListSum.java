package jvmscompare.benchmark;

import jvmscompare.Environment;
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

import static jvmscompare.Environment.PARENT_OPTIONS;
import static jvmscompare.Environment.SIZE;

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
public class IntListSum
{
    private static final String BENCHMARK_INCLUSION_REGEXP = ".*" + IntListSum.class.getSimpleName() + ".*";
    private static final String BENCHMARK_RESULTS_DIRECTORY = "benchmark-results/int-list-sum/";

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
    public long sum_JDK_Boxed_Stream_Serial()
    {
        return this.jdkList.stream().mapToLong(i -> i).sum();
    }

    @Benchmark
    public long sum_EC_Boxed_Eager_Serial()
    {
        return this.ecList.sumOfInt(i -> i);
    }

    @Benchmark
    public long sum_EC_Primitive_Eager_Serial()
    {
        return this.ecIntList.sum();
    }

    @Benchmark
    public long sum_JDK_Boxed_Stream_Parallel()
    {
        return this.jdkList.parallelStream().mapToLong(i -> i).sum();
    }

    @Benchmark
    public long sum_EC_Primitive_Stream_Parallel()
    {
        return this.ecIntList.primitiveParallelStream().asLongStream().sum();
    }

    @Benchmark
    public long sum_EC_Boxed_Lazy_Parallel()
    {
        return this.ecList.asParallel(this.executor, 100_000).sumOfInt(i -> i);
    }

}
