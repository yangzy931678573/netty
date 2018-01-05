package netty.proprietaryProtocol.message;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/3.
 * Description: Netty协议栈的消息
 */
public final class NettyMessage implements Serializable{


    private Header header;//消息头
    private Object body;//消息体

    public final Header getHeader() {
        return header;
    }

    public final void setHeader(Header header) {
        this.header = header;
    }

    public final Object getBody() {
        return body;
    }

    public final void setBody(Object body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "NettyMessage{" +
                "header=" + header +
                ", body=" + body +
                '}';
    }
}
