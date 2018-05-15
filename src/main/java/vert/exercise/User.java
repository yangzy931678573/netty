package vert.exercise;

import io.netty.util.internal.StringUtil;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AbstractUser;
import io.vertx.ext.auth.AuthProvider;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2018/1/16.
 * Description: 用户类
 */
public class User extends AbstractUser {
    private String username;
    private String password;
    //可能存在并发?
    private Map<String, Object> others = new ConcurrentHashMap<>();

    @Override
    protected void doIsPermitted(String s, Handler<AsyncResult<Boolean>> handler) {

    }

    @Override
    public JsonObject principal() {
        JsonObject object = new JsonObject();

        object.put("username", StringUtil.isNullOrEmpty(this.username) ? null : this.username);
        object.put("password", StringUtil.isNullOrEmpty(this.password) ? null : this.password);

        return object;
    }

    @Override
    public void setAuthProvider(AuthProvider authProvider) {
        authProvider.authenticate(principal(), handler -> {
            if (handler.succeeded()) {
                JsonObject principal = handler.result().principal();
                String username = principal.getString("username");
                String password = principal.getString("password");
                System.out.println(username + password);
            }


        });
    }
}
