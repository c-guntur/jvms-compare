package jvmscompare;

import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.list.primitive.IntList;
import org.eclipse.collections.impl.factory.primitive.IntLists;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.list.mutable.primitive.IntArrayList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * This is a set of unit tests to validate the code is working as expected.
 */
public class IntListTest
{
    private static final int SIZE = 1_000_000;

    private List<Integer> jdkList;
    private IntList ecIntList;
    private MutableList<Integer> ecList;
    private ExecutorService executor;
    private long expectedSum;

    @Before
    public void setUp()
    {
        this.executor = Executors.newWorkStealingPool();
        PrimitiveIterator.OfInt iterator =
                new Random(1L)
                        .ints(-1000, 1000)
                        .iterator();
        this.ecList = FastList.newWithNValues(SIZE, iterator::nextInt);
        this.jdkList = new ArrayList<>(SIZE);
        this.jdkList.addAll(this.ecList);
        this.ecIntList = this.ecList.collectInt(i -> i, new IntArrayList(SIZE));
        this.initializeExpectedSum();
    }

    public void initializeExpectedSum()
    {
        this.expectedSum = 0;
        for (Integer integer : this.jdkList)
        {
            this.expectedSum += integer.longValue();
        }
    }

    @Test
    public void sum()
    {
        Assert.assertEquals(this.expectedSum, this.jdkList.stream().mapToLong(i -> i).sum());
        Assert.assertEquals(this.expectedSum, this.jdkList.parallelStream().mapToLong(i -> i).sum());
        Assert.assertEquals(this.expectedSum, this.ecIntList.sum());
        Assert.assertEquals(this.expectedSum, this.ecList.sumOfInt(i -> i));
        Assert.assertEquals(this.expectedSum, this.ecList.asParallel(this.executor, 100_000).sumOfInt(i -> i));
    }

    @Test
    public void filter()
    {
        Assert.assertEquals(this.jdkList.stream().filter(i -> i % 2 == 0).collect(Collectors.toList()),
                            this.ecList.select(i -> i % 2 == 0));
        Assert.assertEquals(this.ecIntList.select(i -> i % 2 == 0),
                            this.ecList.select(i -> i % 2 == 0).collectInt(Integer::intValue));
        Assert.assertEquals(this.jdkList.parallelStream().filter(i -> i % 2 == 0).collect(Collectors.toList()),
                            this.ecList.asParallel(this.executor, 100_000).select(i -> i % 2 == 0).toList());
    }

    @Test
    public void transform()
    {
        Assert.assertEquals(this.jdkList.stream().mapToInt(i -> i * 2).boxed().collect(Collectors.toList()),
                            this.ecList.collect(i -> i * 2).toList());
        Assert.assertEquals(this.ecIntList.collectInt(i -> i * 2, IntLists.mutable.empty()),
                            this.ecList.collect(i -> i * 2).collectInt(Integer::intValue).toList());
        Assert.assertEquals(this.jdkList.parallelStream().mapToInt(i -> i * 2).boxed().collect(Collectors.toList()),
                            this.ecList.asParallel(this.executor, 100_000).collect(i -> i * 2).toList());
    }
}
