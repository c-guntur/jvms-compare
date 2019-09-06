package jvmscompare;

public class JavaInformation {

    public void printJavaInformation()
    {
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
    }
}
