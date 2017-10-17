package AIO01;

/**
 * Created by Administrator on 2017/10/17.
 * Description: AIO客户端
 */
public class TimeClient {
    public static void main(String[] args) {
        int port = 8080;

        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
            }
        }
        new Thread(new AsyncTimeClientHandler("127.0.0.1", port),
                "AIO-AsyncTimeClientHandler-001").start();

    }
}
