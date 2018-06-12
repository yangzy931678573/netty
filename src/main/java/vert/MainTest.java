package vert;

/*
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;*/

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import vert.wiki.Portfolio;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Administrator on 2018/5/10.
 * Description : vertx 测试类
 */
/*@DisplayName("👋 A fairly basic test example")
@ExtendWith(VertxExtension.class)
@Nested*/
public class MainTest {
    public static void main(String[] args) {
        JsonObject object = new JsonObject();
       /* SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");*/
        HashMap<String, Integer> map = new HashMap<>();
        map.put("share1",10);
        object.put("shares",map).put("cash",1.0).put("date",Instant.now() );
        System.out.println( new Portfolio(object));
       /* Portfolio portfolio = object.mapTo(Portfolio.class);
        System.out.println(portfolio);*/
        LocalDateTime parse = LocalDateTime.parse("2016-09-01T11:55:01");
        System.out.println(parse);
        LocalDateTime parse2 = parse.plusDays(-1);
        System.out.println(parse2);
    }
   /* Vertx vertx;

    @BeforeEach
    void prepare() {
        vertx = Vertx.vertx(new VertxOptions()
                .setMaxEventLoopExecuteTime(1000)
                .setPreferNativeTransport(true)
                .setFileResolverCachingEnabled(true));
    }

    @Test
    @DisplayName("⬆️ deploy")
    void deploy(VertxTestContext testContext) {
        vertx.deployVerticle(new MainApplication(), testContext.succeeding(id -> testContext.completeNow()));
    }


    @AfterEach
    void cleanup() {
        vertx.close();
    }*/

}
