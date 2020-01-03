package jvmscompare.benchmark;

import jvmscompare.JavaInformation;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.list.primitive.IntList;
import org.eclipse.collections.impl.factory.primitive.IntLists;
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

import static jvmscompare.Environment.PARENT_OPTIONS;
import static jvmscompare.Environment.SIZE;

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
public class IntListTransform
{
    private static final String BENCHMARK_INCLUSION_REGEXP = ".*" + IntListTransform.class.getSimpleName() + ".*";
    private static final String BENCHMARK_RESULTS_DIRECTORY = "benchmark-results/int-list-transform/";

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
    public List<Integer> transform_JDK_Boxed_Stream_Serial()
    {
        return this.jdkList.stream().mapToInt(i -> i * 2).boxed().collect(Collectors.toList());
    }

    @Benchmark
    public MutableList<Integer> transform_EC_Eager_Serial()
    {
        return this.ecList.collect(i -> i * 2).toList();
    }

    @Benchmark
    public IntList transform_EC_Primitive_Eager_Serial()
    {
        return this.ecIntList.collectInt(i -> i * 2, IntLists.mutable.empty());
    }

    @Benchmark
    public List<Integer> transform_JDK_Boxed_Stream_Parallel()
    {
        return this.jdkList.parallelStream().mapToInt(i -> i * 2).boxed().collect(Collectors.toList());
    }

    @Benchmark
    public IntList transform_EC_Primitive_Stream_Parallel()
    {
        return IntLists.mutable.withAll(
                this.ecIntList.primitiveParallelStream()
                        .map(i -> i * 2));
    }

    @Benchmark
    public MutableList<Integer> transform_EC_Eager_Parallel()
    {
        return this.ecList.asParallel(this.executor, 100_000).collect(i -> i * 2).toList();
    }

}
