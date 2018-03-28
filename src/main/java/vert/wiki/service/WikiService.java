package vert.wiki.service;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * Created by Administrator on 2018/2/2.
 * Description:
 */
@ProxyGen
public interface WikiService {
    @Fluent
    WikiService fetchAllPages(Handler<AsyncResult<JsonArray>> resultHandler);

    @Fluent
    WikiService fetchPage(String name, Handler<AsyncResult<JsonObject>> resultHandler);

    @Fluent
    WikiService createPage(String title, String markdown, Handler<AsyncResult<Void>> resultHandler);

    @Fluent
    WikiService savePage(int id, String markdown, Handler<AsyncResult<Void>> resultHandler);

    @Fluent
    WikiService deletePage(int id, Handler<AsyncResult<Void>> resultHandler);
}
