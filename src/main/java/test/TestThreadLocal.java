package test;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018/1/4.
 * Description:
 */
public class TestThreadLocal {
    private static ThreadLocal<SimpleDateFormat> threadLocal;
    private static ThreadLocal<String> local;

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    static {
        //初始化方法
        threadLocal = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        //初始化方法
        local = ThreadLocal.withInitial(() -> new String("Hello World"));
    }

    @Test
    public void multiThreadTest() {
        ThreadPoolExecutor executor = new
                ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
                100,
                120L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));
        for (int i = 0; i < 10; i++)
            executor.execute(() -> {
                local.set("");
                Thread thread = Thread.currentThread();
                System.out.println(local.get());
              /*  threadLocal.set(new SimpleDateFormat("yyyy-MM-dd"));//
                try {

                    SimpleDateFormat format = threadLocal.get();
                    String result = format.parse("2017-12-13 15:17:27").toString();
                    System.out.println(Thread.currentThread().getName()
                            + result);
                } catch (ParseException e) {
                    e.printStackTrace();
                }*/
            });
        /*System.out.println("-------------------------------------------");*/
        for (int i = 0; i < 10; i++)
            executor.execute(() -> {
                System.out.println(local.get());
               /* try {
                    String result = threadLocal.get().parse("2018-01-05 15:17:27").toString();
                    System.out.println(Thread.currentThread().getName()
                            + result);
                } catch (ParseException e) {
                    e.printStackTrace();
                }*/
            });

    }
}
