package vert.exercise;

import io.vertx.core.*;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.auth.ChainAuth;
import io.vertx.ext.auth.KeyStoreOptions;
import io.vertx.ext.auth.User;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import io.vertx.ext.auth.shiro.ShiroAuth;
import io.vertx.ext.auth.shiro.ShiroAuthOptions;
import io.vertx.ext.auth.shiro.ShiroAuthRealmType;
import io.vertx.ext.web.Cookie;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.Session;
import io.vertx.ext.web.handler.*;
import io.vertx.ext.web.impl.CookieImpl;
import io.vertx.ext.web.sstore.LocalSessionStore;
import io.vertx.ext.web.sstore.SessionStore;

import java.util.Set;


/**
 * Created by Administrator on 2018/1/15.
 * Description:
 */
public class StartVerticleApplication extends AbstractVerticle {
    static int i = 0;

    public static void main(String[] args) {
        //创建一个 vert 实例
        Vertx vertx = Vertx.vertx();

        // 创建一个路由器
        Router router = Router.router(vertx);
        //身份认证器实例
        AuthProvider authProvider = ChainAuth.create();
        //创建一个本地session仓库用于管理本地session
        SessionStore sessionStore = LocalSessionStore.create(vertx);
        HttpServerOptions options = new HttpServerOptions();
        router.route() //一个具体的路由,不指定路径应该是全局路由
                //Cookie处理器
                .handler(CookieHandler.create())
                //使用session处理器,必须先使用Cookie处理器
                .handler(SessionHandler.create(sessionStore))
                //用户session处理器
                .handler(UserSessionHandler.create(authProvider))
                //日志处理器
                .handler(LoggerHandler.create());
        //响应时间处理器
        ResponseTimeHandler.create();
        //响应头Content Type处理器
        ResponseContentTypeHandler.create();

        // CSRFHandler 可以避免跨站点的伪造请求;这个处理器会向所有的 GET 请求的响应里加一个独一无二的令牌作为 Cookie
        // 客户端会在消息头里包含这个令牌,由于令牌基于 Cookie,因此需要在 Router 上启用 Cookie 处理器
        router.route().handler(CookieHandler.create());
        router.route().handler(CSRFHandler.create("vert.x"));

        //认证授权处理器
        AuthHandler authHandler = BasicAuthHandler.create(authProvider);
        router.route("/private").handler(authHandler).handler(StaticHandler.create());

        router.route("/public").handler(context -> {
            User user = context.user();
            System.out.println(user);
        });
        //静态资源处理器
        StaticHandler staticHandler = StaticHandler.create("");
        staticHandler.setCachingEnabled(false);
        router.route().handler(staticHandler);
        //Vert.x Web 通过内置的处理器 FaviconHandler 来提供网页图标。

        // 图标可以指定为文件系统上的某个路径,否则 Vert.x Web 默认会在 classpath 上寻找 favicon.ico 文件;
        // 这意味着您可以将图标打包到您的应用的 jar 包里。


        //跨域请求, CorsHandler 来为您处理 CORS 协议
        router.route().handler(CorsHandler.create("vertx\\.io").allowedMethod(HttpMethod.GET));


        AuthProvider auth = ShiroAuth.create(vertx, new ShiroAuthOptions()
                .setType(ShiroAuthRealmType.PROPERTIES)
                .setConfig(new JsonObject()
                        .put("properties_path", "classpath:wiki-users.properties")));
        //线程会阻塞
        router.get("/index1").handler(context -> {
            System.out.println(Thread.currentThread().getName());
        });
        router.get("/index2").handler(context -> {
            System.out.println(Thread.currentThread().getName());
        });
        router.route(HttpMethod.GET, "/index").handler(context -> {
            Set<Cookie> cookies = context.cookies();
            Cookie cookie = new CookieImpl("web", "index");
            cookie.setDomain("/").setMaxAge(3000).setHttpOnly(true);
            cookies.add(cookie);

            HttpServerRequest request = context.request();
            String scheme = request.scheme();
            System.out.println("scheme : " + scheme);// scheme : http
            HttpServerResponse response = context.response();
            Session session = context.session();
            session.put("Vert.x", "Web");
            System.out.println(session.id());//
            //context.setSession(session);
            response.end("Hello World");
        });
        Router apiRouter = Router.router(vertx);

        JWTAuth jwtAuth = JWTAuth.create(vertx, new JWTAuthOptions()
                .setKeyStore(
                        new KeyStoreOptions()
                                .setPath("keystore.jceks")
                                .setType("jceks")
                                .setPassword("secret")));

        apiRouter.route().handler(JWTAuthHandler.create(jwtAuth, "/api/token"));


        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
/*
        new Thread(()->vert.createHttpClient().get("localhost:8080/index1").connection());
        new Thread(()->vert.createHttpClient().get("localhost:8080/index2").connection());*/
      /*  vert.createHttpServer().requestHandler(request -> {
                    //    /favicon.ico 被浏览器默认请求
                    System.out.println(request.uri());
                    for (Map.Entry<String, String> header : request.headers()) {
                        System.out.println(header.getKey() + " : " + header.getValue());
                    }
                   *//* byte[] bytes = "<html><body><h1>Hello World</h1></body></html>".getBytes();*//*
                    HttpServerResponse response = request.response();
                   *//* response.setStatusCode(200)
                            .setStatusMessage("OK")
                            .setChunked(true)
                            .write(Buffer.buffer().appendBytes(bytes)).end();*//*
                    response.end("Hello World");

                }
        ).listen(8080);*/
    }

}
