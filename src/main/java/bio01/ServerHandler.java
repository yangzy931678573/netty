package bio01;

import java.io.*;
import java.net.Socket;
import java.util.Date;

/**
 * Created by Administrator on 2017/10/9.
 * Description:
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
            String currentTime;
            String body;
            while (true) {

                body = in.readLine();
                if (body == null) {
                    break;
                }
                System.out.println("The server receive order : " + body);
                currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ?
                        new Date(System.currentTimeMillis()).toString()
                        : "BAD ORDER";
                out.println(currentTime);
                currentTime.indexOf("");
              /*  File file = new File("src/bio01/index.html");

                if (file.exists()) {
                    FileInputStream inputStream = new FileInputStream(file);

                    long length = file.length();
                    InputStream inputStream = ServerHandler.class.getResourceAsStream("/bio01/index.html");
                    String responseHeaders = "HTTP/1.1 200 OK\r\n" +
                            "Content-Type: text/html\r\n" +
                            "Content-Length: " + length + "\r\n" +
                            "\r\n";
                    out.println(responseHeaders);
                    byte[] bytes = new byte[1024];
                    int buffer = inputStream.read(bytes, 0, 1024);
                    while (buffer != -1) {
                        out.println(new String(bytes));
                        buffer = inputStream.read(bytes, 0, 1024);
                    }
                } else {
                    //Content-Length:限制了响应内容的长度,只包括响应体的长度
                    String contentBody = "<h1>File Not Found.</h1><br/>Please check your request.";
                    int length = contentBody.length();

                    currentTime = "HTTP/1.1 404 File Not Found\r\n" +
                            "Content-Type: text/html\r\n" +
                            "Content-Length: " + length + "\r\n" +
                            "\r\n" +
                            contentBody;
                    out.println(currentTime);

                }*/
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
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
                this.socket = null;
            }
        }
    }
}
