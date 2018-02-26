package vert;

import io.vertx.core.*;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.auth.ChainAuth;
import io.vertx.ext.auth.User;
import io.vertx.ext.auth.jwt.JWTAuth;
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
                .handler(UserSessionHandler.create(authProvider));
        //认证授权处理器
        AuthHandler authHandler = BasicAuthHandler.create(authProvider);
        router.route("/private").handler(authHandler).handler(StaticHandler.create());

        router.route("/public").handler(context -> {
            User user = context.user();
            System.out.println(user);
        });
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

        JWTAuth jwtAuth = JWTAuth.create(vertx, new JsonObject()
                .put("keyStore", new JsonObject()
                        .put("path", "keystore.jceks")
                        .put("type", "jceks")
                        .put("password", "secret")));

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
