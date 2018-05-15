package vert;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Administrator on 2018/2/5.
 * Description: Junit4 测试类
 */
@RunWith(VertxUnitRunner.class)
public class Junit4Test {
    private Vertx vertx;

    @Before
    public void init(TestContext context) {
        vertx = Vertx.vertx(new VertxOptions()
                .setMaxEventLoopExecuteTime(1000)
                .setPreferNativeTransport(true)
                .setFileResolverCachingEnabled(true));
        int port = 80;
        DeploymentOptions options = new DeploymentOptions()
                .setConfig(new JsonObject()
                        .put("http.port", port));
        vertx.deployVerticle(MainApplication.class.getName(), options, context.asyncAssertSuccess());
    }

    @Test
    public void start(TestContext context) {
        Async async = context.async();
        System.out.println(" 👋 a test application start ... ");
    }

    @After
    public void finish(TestContext context) {
        vertx.close(context.asyncAssertSuccess());
    }
}
