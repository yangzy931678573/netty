package bio01;

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
            while (true) {

                socket = server.accept(); //程序阻塞在这里:如果启动线程来接受socket，就会导致无限多的线程被创建。阻塞是对资源的保护。

                //创建线程处理请求
                new Thread(new ServerHandler(socket)).start();
                //进入下次循环
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
