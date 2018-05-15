package vert;

import io.vertx.codegen.annotations.Nullable;
import io.vertx.core.*;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2018/5/10.
 * Description : HTTP 服务器
 */
public class MainApplication extends AbstractVerticle {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainApplication.class);

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx(new VertxOptions()
                .setMaxEventLoopExecuteTime(1000)
                .setPreferNativeTransport(true)
                .setFileResolverCachingEnabled(true));
        int port = 80;
        DeploymentOptions options = new DeploymentOptions()
                .setConfig(new JsonObject()
                        .put("http.port", port));
        vertx.deployVerticle(MainApplication.class.getName(),
                options);
    }

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        Future<Void> future = startHttpServer();
    }

    private Future<Void> startHttpServer() {
        Future<Void> future = Future.future();
        HttpServer server = vertx.createHttpServer();

        Router router = Router.router(vertx);
        router.exceptionHandler(this::exceptionHandler);

        router.route()
                //浏览器图标
                .handler(FaviconHandler.create("webroot/static/panda.png"))
                //cookie
                .handler(CookieHandler.create())
                //静态资源
                .handler(StaticHandler.create("webroot/static"))
                //日志
                .handler(LoggerHandler.create());


      /*
      //跨域
        Set<String> allowedHeaders = new HashSet<>();
        allowedHeaders.add("x-requested-with");
        allowedHeaders.add("Access-Control-Allow-Origin");
        allowedHeaders.add("origin");
        allowedHeaders.add("Content-Type");
        allowedHeaders.add("accept");
        allowedHeaders.add("X-PINGARUNER");

        Set<HttpMethod> allowedMethods = new HashSet<>();
        allowedMethods.add(HttpMethod.GET);
        allowedMethods.add(HttpMethod.POST);
        allowedMethods.add(HttpMethod.OPTIONS);
        router.route().handler(CorsHandler.create("*")
                .allowedHeaders(allowedHeaders)
                .allowedMethods(allowedMethods));*/
        //主页
        router.route("/:id").handler(this::index);//id不能为空
        //表单提交
        router.post().handler(BodyHandler.create());

        int port = config().getInteger("http.port", 80);
        server
                .requestHandler(router::accept)
                .listen(port, asyncResult -> {
                    if (asyncResult.succeeded()) {
                        LOGGER.info(" HTTP server running on port : " + port);
                        future.complete();
                    } else {
                        LOGGER.error(" Could not start a HTTP server ", asyncResult.cause());
                        future.fail(asyncResult.cause());
                    }
                });

        return future;
    }


    private void index(RoutingContext context) {
        String id = context.request().getParam("id");
        System.out.println(id);
        context.reroute(HttpMethod.GET,"html/index.html");
    }

    private void exceptionHandler(Throwable th) {
        LOGGER.info(" an exception information happened ");
        th.printStackTrace();
    }

}
