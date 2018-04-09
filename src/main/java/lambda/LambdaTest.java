package lambda;

import io.vertx.core.Handler;
import io.vertx.core.impl.ContextTask;
import org.junit.Test;

import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by Administrator on 2018/1/17.
 * Description: Lambda表达式测试
 */
public class LambdaTest implements Supplier{
    public LambdaTest(){

    }
    Runnable rr1 = () -> {
        System.out.println(this);
    };
    Runnable rr2 = () -> {
        System.out.println(toString());
    };

    public String toString() {
        return "Hello, world";
    }

    int o = 0;
    Runnable r1 = new Runnable() {
        @Override
        public void run() {
            int o = 0;
            System.out.println(this);
        }
    };
    Runnable r2 = new Runnable() {
        @Override
        public void run() {
            System.out.println(toString());
        }
    };

    @Test
    public void test2() {
        System.out.println(new LambdaTest());
        int i = 0;
        //报错
        Runnable runnable1 = () -> {
             o = 0;
            System.out.println(i);//可以直接访问局部变量,但是不能赋值,原因是局部变量被认为是final类型
        };
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int i = 0;//不报错
                System.out.println(toString());
            }
        };
        //使用匿名内部类
        new LambdaTest().r1.run();//lambda.LambdaTest$1@726f3b58,应当是匿名内部类所属的对象
        new LambdaTest().r2.run();//lambda.LambdaTest$2@442d9b6e
        //使用 Lambda表达式
        new LambdaTest().rr1.run();//Hello, world
        new LambdaTest().rr2.run();//Hello, world
        Function<LambdaTest, String> function = LambdaTest::toString;
        Comparator<LambdaTest> comparing = Comparator.comparing(function);
        Arrays.sort(new  LambdaTest[0],comparing);

    }
    public static void  test4(){
    }
    @Test
    public void test() {
        CallBack<String> back = new CallBack<>();
        System.out.println(back.i);
        Boolean b = false;
        Callback flag = (f1, f2) -> System.out.println();
        FileFilter filter = f -> f.getName().endsWith("");
       // Object o = new Object();
        Stream<String> stream = new ArrayList<>().stream().map(Object::toString);

        Comparator<Object> comparator = Comparator.comparing(f -> f.toString());
        ArrayList<LambdaTest> tests = new ArrayList<>();
        tests.sort(Comparator.comparing(LambdaTest::toString));
        int[] ints = new int[]{1};
        IntStream intStream = Arrays.stream(ints);
    }
    @Test
    public void test3() {

    }
    public void functionA(){
        System.out.println("start invoke functionA");
        functionB();
        System.out.println("end invoke functionA");
    }
    public void functionB(){
        System.out.println("start invoke functionB");
        functionA();
        System.out.println("end invoke functionB");

    }
    @Override
    public Object get() {
        return null;
    }



    class CallBack<K> {
        int i = 0;
        //protected abstract K callBack();
        void test(){
            System.out.println(LambdaTest.this.o);
        }
    }
}

interface Callback<T> {
    void callback(T t1, T t2);

    //T callback2();
}


