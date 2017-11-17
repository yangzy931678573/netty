package bio02;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Administrator on 2017/10/9.
 * Description:   netty服务器启动器
 */
public class BootStrap {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        //
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {

                e.printStackTrace();
            }
        }
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            System.out.println("The server is starting in port : " + port);
            Socket socket;
            ServerHandlerExecutePool singleExecutor = new ServerHandlerExecutePool(50,1000);
            while (true) {
                socket = server.accept();
                //这里使用线程池来控制线程资源，大大的减少了内存开销
                singleExecutor.execute(new ServerHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (server != null) {
                System.out.println("The time server close");
                server.close();
                server = null;
            }
        }
    }
}
