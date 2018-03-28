package test;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Administrator on 2018/3/27.
 * Description :
 */
public class TestLambda {
    @Test
    public void test() {
        String[] strings = {"Hello", "World"};

        //使用map

     /*   List<Stream<String>> streamList = Arrays.asList(strings).stream().
                map(str -> str.split("")).
                map(str -> Arrays.stream(str)).
                collect(Collectors.toList());*/
        //使用map)
        Stream<String[]> stream = Arrays.asList(strings).stream().
                map(str -> str.split(""));
        List<String[]> strings11 = stream.collect(Collectors.toList());
        strings11.forEach(strings2 -> Arrays.stream(strings2).forEach(strings3 -> System.out.print(" (" + strings3 + ") ")));
/*
        Stream<Stream<String>> streamStream = stream.map(strings1 -> Arrays.stream(strings1));
        List<Stream<String>> streamList1 = streamStream.collect(Collectors.toList());*/


        List<String> stringList = Arrays.asList(strings).stream().
                map(str -> str.split("")).
                flatMap(str -> Arrays.stream(str))
                .collect(Collectors.toList());
        System.out.println();
        stringList.forEach(s -> System.out.println(s));


        //分步写(流只能消费一次)(flatMap)
        Stream<String[]> stream1 = Arrays.asList(strings).stream().
                map(str -> str.split(""));

        Stream<String> stringStream = stream1.flatMap(strings1 -> Arrays.stream(strings1));

        List<String> stringList1 = stringStream.collect(Collectors.toList());

        //对flatMap的说明：这个在这里的主要作用是对流进行扁平化
    }

    @Test
    public void test1() {
        ExecutorService executorService = new ThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors(),//使用cpu核数作为线程池主线程数量
                50, 120L,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000));
        executorService.execute(() -> {
            try {
                System.out.println(Thread.currentThread().getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        System.out.println(Thread.currentThread().getName());
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("end");
    }

    @Test
    public void test2() throws InterruptedException {
        Thread t = new Thread(() -> {

            System.out.println("First task started");
            System.out.println("Sleeping for 2 seconds");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("First task completed");

        });
        Thread t1 = new Thread(() -> System.out.println("Second task completed"));
        t.start(); // Line 15
        t.join(); // Line 16
        t1.start();
    }
}

