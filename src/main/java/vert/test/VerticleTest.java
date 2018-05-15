package vert.test;

import io.netty.channel.socket.nio.NioSocketChannel;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import org.junit.Test;
import org.junit.runner.RunWith;
import vert.wiki.MainVerticle;

import java.net.Socket;
import java.nio.channels.SocketChannel;

/**
 * Created by Administrator on 2018/2/5.
 * Description:
 */
//@RunWith(VertxUnitRunner.class)
public class VerticleTest {

    @Test /*(timeout=5000)*/
    public void asyncBehavior(TestContext context) {
        Vertx vertx = Vertx.vertx();
        context.assertEquals("foo", "foo");
        Async a1 = context.async();
        Async a2 = context.async(3);
        vertx.setTimer(100, n -> {
            a1.complete();
            System.out.println("a1 : " + n);
        });
        vertx.setPeriodic(100, n -> {
            a2.countDown();
            System.out.println("a2 : " + n);
        });
        HttpClient httpClient = vertx.createHttpClient();
        httpClient.get("");
    }

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        WebClient webClient = WebClient.create(vertx);
      /*  webClient.get(8080, "127.0.0.1", "/index").send(asyncResult -> {
            if (asyncResult.succeeded()) {
                HttpResponse<Buffer> response = asyncResult.result();
                System.out.println("Received response with status code : " + response.statusCode());
                System.out.println(response.bodyAsBuffer().toString());
                vertx.close();
            } else {
                System.out.println("Something went wrong : " + asyncResult.cause().getMessage());
            }
        });*/
        webClient.get(443,"www.hao123.com","/")//get方法是组合路径 ,getAbs是全路径
                .addQueryParam("tn","93006350_hao_pg")
                //.as(BodyCodec.jsonObject())解码http response
                .timeout(5000)
                .ssl(true)
                .send(asyncResult -> {
            if (asyncResult.succeeded()) {
                HttpResponse<Buffer> response = asyncResult.result();
                System.out.println("Received response with status code : " + response.statusCode());
                System.out.println(response.bodyAsBuffer().toString());
                vertx.close();
            } else {
                System.out.println("Something went wrong : " + asyncResult.cause().getMessage());
            }
        });

        //HTTPClientApi
       /*      HttpClient httpClient = vertx.createHttpClient(new HttpClientOptions()
                .setDefaultHost("127.0.0.1")
                .setMaxPoolSize(20)
                .setDefaultPort(8080));  */
       /* HttpClient httpClient = vertx.createHttpClient();
        HttpClientRequest request = httpClient.get(8080, "127.0.0.1", "/index").handler(response -> {
            response.exceptionHandler(t -> {
                t.printStackTrace();
                httpClient.close();
                System.out.println("close");
            });
            response.bodyHandler(buffer -> {
                System.out.println(buffer.toString());
                httpClient.close();
                System.out.println("close");
            });
        }).putHeader("Accept", "application/json")
                .setTimeout(3000)
                .exceptionHandler(throwable -> throwable.printStackTrace());*/
       /* HttpClientRequest request = httpClient.get("/index", response -> {
            response.exceptionHandler(t -> {
                t.printStackTrace();
                httpClient.close();
            });
            if (response.statusCode() == 200)
                response.bodyHandler(buffer -> System.out.println(buffer.toString()));
        }).putHeader("Accept", "application/json")
                .setTimeout(3000)
                .exceptionHandler(throwable -> throwable.printStackTrace());
       request.end();
       */
        System.out.println("end");
    }

   /* @Before
    public void prepare(TestContext context) throws InterruptedException {

    }

    @After
    public void finish(TestContext context) {
       // vertx.close(context.asyncAssertSuccess());
    }*/
}
