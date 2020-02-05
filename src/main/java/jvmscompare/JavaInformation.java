package jvmscompare;

public class JavaInformation {

    public static void main(String[] args) {
        new JavaInformation().printJavaInformation();
    }

    public void printJavaInformation()
    {
        int mb = 1024*1024;

        //Getting the runtime reference from system
        Runtime runtime = Runtime.getRuntime();

        System.out.println("##### Heap utilization statistics [MB] #####");

        //Print used memory
        System.out.println("Used Memory:"
                + (runtime.totalMemory() - runtime.freeMemory()) / mb);

        //Print free memory
        System.out.println("Free Memory:"
                + runtime.freeMemory() / mb);

        //Print total available memory
        System.out.println("Total Memory:" + runtime.totalMemory() / mb);

        //Print Maximum available memory
        System.out.println("Max Memory:" + runtime.maxMemory() / mb);

        System.setProperty("java.vm.name", "anonymous");
        System.out.println("Java Runtime Environment version: " + System.getProperty("java.version"));
        System.out.println("Java Runtime Environment vendor: " + System.getProperty("java.vendor"));
        System.out.println();
        System.out.println("Java Runtime Name: " + System.getProperty("java.runtime.name"));
        System.out.println("Java Runtime Version: "+ System.getProperty("java.runtime.version"));
        System.out.println();
        System.out.println("Java Virtual Machine name: " +System.getProperty("java.vm.name"));
        System.out.println("Java Virtual Machine vendor: " + System.getProperty("java.vm.vendor"));
        System.out.println("Java Virtual Machine version: " + System.getProperty("java.vm.version"));
        System.out.println();
        System.out.println("Java Class Version: "+ System.getProperty("java.class.version"));
        System.out.println();
        System.out.println("Benchmark size: "+ Environment.SIZE);
        System.out.println("Benchmark forks: "+ Environment.FORKS);
        System.out.println("Benchmark warmups: "+ Environment.WARMUP_ITERATIONS);
        System.out.println("Benchmark measurements: "+ Environment.MEASUREMENT_ITERATIONS);
        System.out.println();
    }
}
