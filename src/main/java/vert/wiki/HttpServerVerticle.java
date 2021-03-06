package vert.wiki;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.templ.FreeMarkerTemplateEngine;

import java.util.Date;


/**
 * Created by Administrator on 2018/2/2.
 * Description: HttpServer Verticle
 */
public class HttpServerVerticle extends AbstractVerticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpServerVerticle.class);

    private final FreeMarkerTemplateEngine templateEngine = FreeMarkerTemplateEngine.create();

    public static final String CONFIG_HTTP_SERVER_PORT = "http.server.port";

    public static final String CONFIG_WIKI_DB_QUEUE = "wikiDb.queue";

    private String wikiDbQueue;

    @Override
    public void start(Future<Void> startFuture) throws Exception {

        wikiDbQueue = config().getString(CONFIG_WIKI_DB_QUEUE, "wikiDb.queue");//后一个参数为默认值

        HttpServer server = vertx.createHttpServer();

        Router router = Router.router(vertx);

        router.route().handler(this::loggerHandler);
        router.exceptionHandler(throwable -> throwable.printStackTrace());
        router.get("/").handler(this::indexHandler);
        router.get("/wiki/:page").handler(this::pageRenderingHandler);
        router.post().handler(BodyHandler.create());
        router.post("/save").handler(this::pageUpdateHandler);
        router.post("/create").handler(this::pageCreateHandler);
        router.post("/delete").handler(this::pageDeletionHandler);

        Route route = router.route(HttpMethod.PUT, "myapi/orders")
                //设置默认接收json类型参数
                .consumes("application/json")
                //设置默认返回json类型参数
                .produces("application/json");

        //将子路由器挂载到主路由器上
        Router mainRouter = Router.router(vertx);
        mainRouter.mountSubRouter("/productsAPI", router);
        // 正确的方式
        router.get().handler(ctx -> {
            //使用一个新的参数重定向到另外一个路径
            ctx.put("variable", "value").reroute("/final-target");//转发
        });
        route.handler(routingContext -> {

            // 这会匹配所有路径以 `/myapi/orders` 开头,
            // `content-type` 值为 `application/json` 并且 `accept` 值为 `application/json` 的 PUT 请求

        });

        int portNumber = config().getInteger(CONFIG_HTTP_SERVER_PORT, 80);//后一个参数为默认值
        server
                .requestHandler(router::accept)
                .listen(portNumber, ar -> {
                    if (ar.succeeded()) {
                        LOGGER.info("HTTP server running on port " + portNumber);
                        startFuture.complete();
                    } else {
                        LOGGER.error("Could not start a HTTP server", ar.cause());
                        startFuture.fail(ar.cause());
                    }
                });
    }


    private static final String EMPTY_PAGE_MARKDOWN =
            "# A new page\n" +
                    "\n" +
                    "Feel-free to write in Markdown!\n";

    //全局日志处理
    private void loggerHandler(RoutingContext context) {
        LOGGER.info(context.request().uri());
        context.next();//否则阻塞到这里
    }

    private void pageRenderingHandler(RoutingContext context) throws RuntimeException {

        String requestedPage = context.request().getParam("page");
        JsonObject request = new JsonObject().put("page", requestedPage);

        DeliveryOptions options = new DeliveryOptions().addHeader("action", "get-page");
        vertx.eventBus().send(wikiDbQueue, request, options, reply -> {

            if (reply.succeeded()) {
                JsonObject body = (JsonObject) reply.result().body();

                boolean found = body.getBoolean("found");
                String rawContent = body.getString("rawContent", EMPTY_PAGE_MARKDOWN);//后一个参数为默认值
                context.put("title", requestedPage);
                context.put("id", body.getInteger("id", -1));
                context.put("newPage", found ? "no" : "yes");
                context.put("rawContent", rawContent);
                context.put("content", rawContent);
                context.put("timestamp", new Date().toString());

                templateEngine.render(context, "templates", "/page.ftl", ar -> {
                    if (ar.succeeded()) {
                        context.response().putHeader("Content-Type", "text/html").end(ar.result());
                    } else {
                        context.fail(ar.cause());
                    }
                });
                HttpServerResponse response = context.response();
            } else {
                context.fail(reply.cause());
            }
        });
    }

    private void pageUpdateHandler(RoutingContext context) {

        String title = context.request().getParam("title");
        JsonObject request = new JsonObject()
                .put("id", context.request().getParam("id"))
                .put("title", title)
                .put("markdown", context.request().getParam("markdown"));

        DeliveryOptions options = new DeliveryOptions();
        if ("yes".equals(context.request().getParam("newPage"))) {
            options.addHeader("action", "create-page");
        } else {
            options.addHeader("action", "save-page");
        }

        vertx.eventBus().send(wikiDbQueue, request, options, reply -> {
            if (reply.succeeded())
                context.response().setStatusCode(303).putHeader("Location", "/wiki/" + title).end();
            else
                context.fail(reply.cause());

        });
    }

    private void pageCreateHandler(RoutingContext context) {
        String pageName = context.request().getParam("name");
        String location = "/wiki/" + pageName;
        if (pageName == null || pageName.isEmpty()) {
            location = "/";
        }
        context.response().setStatusCode(303).putHeader("Location", location).end();

    }

    private void pageDeletionHandler(RoutingContext context) {
        String id = context.request().getParam("id");
        JsonObject request = new JsonObject().put("id", id);
        DeliveryOptions options = new DeliveryOptions().addHeader("action", "delete-page");
        vertx.eventBus().send(wikiDbQueue, request, options, reply -> {
            if (reply.succeeded()) {
                context.response().setStatusCode(303).putHeader("Location", "/").end();
            } else {
                context.fail(reply.cause());
            }
        });
    }

    private void indexHandler(RoutingContext context) {
        DeliveryOptions options = new DeliveryOptions().addHeader("action", "all-pages");
        vertx.eventBus().send(wikiDbQueue, new JsonObject(), options, reply -> {
            if (reply.succeeded()) {
                JsonObject body = (JsonObject) reply.result().body();
                context.put("title", "Wiki home")
                        .put("pages", body.getJsonArray("pages").getList());
                templateEngine.render(context, "templates", "/index.ftl", ar -> {
                    if (ar.succeeded()) {
                        context.response().end(ar.result());
                    } else {
                        context.fail(ar.cause());
                    }
                });
            } else {
                context.fail(reply.cause());
            }
        });
    }
}