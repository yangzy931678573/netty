package nio01;

/**
 * Created by Administrator on 2017/10/10.
 * Description: 第一个NIO客户端
 */
public class NIOClient {
    public static void main(String[] args) {
        int port = 8080;
        new Thread(new TimeClientHandle("127.0.0.1",port),"TimeClient-001").start();
    }
}
