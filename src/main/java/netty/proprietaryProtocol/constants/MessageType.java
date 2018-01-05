package netty.proprietaryProtocol.constants;

import sun.applet.resources.MsgAppletViewer_sv;

/**
 * Created by Administrator on 2018/1/5.
 * Description: MessageType消息类型
 */
public enum MessageType {
    //握手请求
    LOGIN_REQ {
        @Override
        public byte value() {
            return 3;
        }
    },
    //握手应答
    LOGIN_RESP {
        @Override
        public byte value() {
            return 4;
        }
    },
    //心跳请求
    HEARTBEAT_REQ{
        @Override
        public byte value() {
            return 5;
        }
    },
    //心跳应答
    HEARTBEAT_RESP{
        @Override
        public byte value() {
            return 6;
        }
    };

    //该方法应该定义成抽象方法
    public abstract byte value();
}
