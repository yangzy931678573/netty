package reactivex;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import org.junit.Test;


/**
 * Created by Administrator on 2018/1/24.
 * Description: ReactiveX - java 测试
 */
public class RxFirst {
    public static void main(String[] args) throws InterruptedException {
        Flowable.just("Start").subscribe(System.out::println);
        Flowable.fromCallable(() -> {
            Thread.sleep(1000);
            return "Done";
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe(System.out::println, Throwable::printStackTrace);
        Flowable.just("Sleep").subscribe(System.out::println);
        Thread.sleep(2000);
    }

    @Test
    public void test() {
        Flowable.range(1, 10)
                .observeOn(Schedulers.computation())
                .map(v -> v * v)
                .blockingSubscribe(System.out::println);
    }

    @Test
    public void test1() {
        Flowable.range(1, 10)
                .flatMap(v ->
                        Flowable.just(v)
                                .subscribeOn(Schedulers.computation())
                                .map(w -> w * w)
                )
                .blockingSubscribe(System.out::println);
    }

    @Test
    public void test2() {
        Flowable.range(1, 10)
                .parallel()
                .runOn(Schedulers.computation())
                .map(v -> v * v)
                .sequential()
                .blockingSubscribe(System.out::println);//blockingSubscribe能阻塞当前线程,防止当前线程结束而计算没有结束
    }

    @Test
    public void test3() {
        Observable.fromArray(new String[]{"Hello ", " My ", " World"}).subscribe(System.out::print);
    }
}
