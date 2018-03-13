package vert.test;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import vert.wiki.MainVerticle;

/**
 * Created by Administrator on 2018/2/5.
 * Description:
 */
@RunWith(VertxUnitRunner.class)
public class VerticleTest {

    @Test /*(timeout=5000)*/
    public void asyncBehavior(TestContext context) {
        Vertx vertx = Vertx.vertx();
        context.assertEquals("foo", "foo");
        Async a1 = context.async();
        Async a2 = context.async(3);
        vertx.setTimer(100, n -> {a1.complete();
            System.out.println("a1 : " + n);});
        vertx.setPeriodic(100, n -> {a2.countDown();
            System.out.println("a2 : " + n);});
    }

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new MainVerticle(), new DeploymentOptions());
    }

   /* @Before
    public void prepare(TestContext context) throws InterruptedException {

    }

    @After
    public void finish(TestContext context) {
       // vertx.close(context.asyncAssertSuccess());
    }*/
}
