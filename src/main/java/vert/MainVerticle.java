package vert;

import io.vertx.core.*;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.asyncsql.MySQLClient;
import io.vertx.ext.sql.SQLClient;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.templ.FreeMarkerTemplateEngine;

/**
 * Created by Administrator on 2018/1/19.
 * Description: 标准 Vertx Main 应用
 * reactivex 是reactive java 2.0
 */
public class MainVerticle extends AbstractVerticle {
    //mysql连接池客户端
    private SQLClient mysqlClient;
    //日志
    private static final Logger LOGGER = LoggerFactory.getLogger(MainVerticle.class);
    //FreeMarker模版引擎
    private final FreeMarkerTemplateEngine freeMarker = FreeMarkerTemplateEngine.create();

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        Future<Void> steps = prepareDatabase().compose(aVoid -> startHttpServer());
        steps.setHandler(asyncResult -> {
            if (asyncResult.succeeded())
                startFuture.complete();
             else
                startFuture.fail(asyncResult.cause());

        });
    }

    @Override
    public void stop(Future<Void> stopFuture) throws Exception {
        super.stop(stopFuture);
    }

    private Future<Void> prepareDatabase() {
        Future<Void> future = Future.future();
        JsonObject mysqlConfig = new JsonObject()
                .put("username", "root")
                .put("password", "Labbook_701")
                .put("charset", "utf-8");
        mysqlClient = MySQLClient.createShared(vertx, mysqlConfig);
        mysqlClient.getConnection(sqlConnAsyncResult -> {
            if (sqlConnAsyncResult.failed()) {
                LOGGER.error("Could not open a database connection : caused by " + sqlConnAsyncResult.cause());
                future.fail(sqlConnAsyncResult.cause());
            } else {
                SQLConnection connection = sqlConnAsyncResult.result();
                /*JsonArray user = new JsonArray().add("张三").add("123456");
                connection.updateWithParams("INSERT INTO USER(username,password) VALUES (?,?)", user, updateAsyncResult -> {
                    if (updateAsyncResult.failed()) {
                        LOGGER.error("Data insert failure : caused by " + updateAsyncResult.cause());
                        future.fail(updateAsyncResult.cause());
                    } else
                        future.complete();
                });*/
            }
        });
        return future;
    }

    private Future<Void> startHttpServer() {
        Future<Void> future = Future.future();
        HttpServer server = vertx.createHttpServer();

        Router router = Router.router(vertx);

        router.post().handler(BodyHandler.create());//处理表单提交
        router.get("/").handler(this::indexHandler);
        router.post("/save").handler(this::saveUserHandler);

        server
                .requestHandler(router::accept)
                .listen(8080, asyncResult -> {
                    if (asyncResult.succeeded()) {
                        LOGGER.info("HTTP server running on port 8080");
                        future.complete();
                    } else {
                        LOGGER.error("Could not start a HTTP server", asyncResult.cause());
                        future.fail(asyncResult.cause());
                    }
                });

        return future;
    }

    private void indexHandler(RoutingContext context) {
        freeMarker.render(context, "templates", "/index.html", bufferAsyncResult -> {
            if (bufferAsyncResult.succeeded()) {
                context.response().putHeader("Content-type", "text/html").end(bufferAsyncResult.result());
            } else
                context.fail(bufferAsyncResult.cause());
        });
    }

    private void saveUserHandler(RoutingContext context) {
        String username = context.request().getParam("username");
        String password = context.request().getParam("password");
        mysqlClient.getConnection(sqlConnAsyncResult -> {
            if (sqlConnAsyncResult.failed()) {
                LOGGER.error("Could not open a database connection : caused by " + sqlConnAsyncResult.cause());
                context.fail(sqlConnAsyncResult.cause());
            } else {
                SQLConnection connection = sqlConnAsyncResult.result();
                JsonArray user = new JsonArray().add(username).add(password);
                connection.updateWithParams("INSERT INTO USER(username,password) VALUES (?,?)", user, updateAsyncResult -> {
                    if (updateAsyncResult.failed()) {
                        LOGGER.error("Data insert failure : caused by " + updateAsyncResult.cause());
                        context.fail(updateAsyncResult.cause());
                    } else {
                        LOGGER.debug("Data insert success");
                        context.response().end("success");
                    }
                });
            }

        });

    }
}
