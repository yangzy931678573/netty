package bio02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

/**
 * Created by Administrator on 2017/10/9.
 * Description: 处理请求的线程
 */
public class ServerHandler implements Runnable {
    private Socket socket;

    public ServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            out = new PrintWriter(this.socket.getOutputStream(), true);
            String currentTime ;
            String body ;
            while (true) {
                System.out.println("success");
                body = in.readLine();
                if (body == null) {
                    break;
                }
                System.out.println("The server receive order : " + body);
                currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ?
                        new Date(System.currentTimeMillis()).toString()
                        : "BAD ORDER";
                out.println(currentTime);
            }
        } catch (IOException e) {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (out != null) {
                out.close();
            }
            if (this.socket != null) {
                try {
                    this.socket.close();
                    this.socket = null ;
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
        }
    }
}
