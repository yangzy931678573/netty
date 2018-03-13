package vert.wiki;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;

/**
 * Created by Administrator on 2018/1/19.
 * Description: 新 Vert.x Main 应用
 */
public class MainVerticle extends AbstractVerticle {

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        //先部署 DatabaseVerticle
        Future<String> dbDeployment = Future.future();
        vertx.deployVerticle(new WikiDatabaseVerticle(), dbDeployment.completer());

        //合并部署 HttpServerVerticle
        dbDeployment.compose(id -> {
            Future<String> deployment = Future.future();
            //利用反射才能部署多个Verticle实例
            vertx.deployVerticle("vert.wiki.HttpServerVerticle",
                    new DeploymentOptions().setInstances(2),//设置 两个实例
                    deployment.completer());

            return deployment;

        }).setHandler(ar -> {
            if (ar.succeeded())
                startFuture.complete();
            else
                startFuture.fail(ar.cause());

        });
    }
}
