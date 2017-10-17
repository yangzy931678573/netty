package nio01;

import java.awt.*;

/**
 * Created by Administrator on 2017/10/10.
 * Description: 第一个NIO服务器
 */
public class BootStrap {
    public static void main(String[] args) {
        int port = 8080;
        MultiplexerTimeServer server = new MultiplexerTimeServer(port);
        new Thread(server,"NIO-MultiplexerTimeServer-001").start();
    }
}
