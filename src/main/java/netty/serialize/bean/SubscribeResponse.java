package netty.serialize.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/27.
 * Description: 订阅消息响应实体类
 */
public class SubscribeResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private int requestId;
    private int responseCode;
    private String describe;

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Override
    public String toString() {
        return "SubscribeResponse{" +
                "requestId=" + requestId +
                ", responseCode=" + responseCode +
                ", describe='" + describe + '\'' +
                '}';
    }
}
