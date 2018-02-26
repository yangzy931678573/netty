package nio01;

/**
 * Created by Administrator on 2017/10/10.
 * Description: 第一个NIO客户端
 */
public class NIOClient {
    public static void main(String[] args) {
        int port = 8080;
        for (int i = 0; i < 10; i++)
            new Thread(new TimeClientHandle("127.0.0.1", port), "TimeClientBootstrap-001").start();
    }
}
