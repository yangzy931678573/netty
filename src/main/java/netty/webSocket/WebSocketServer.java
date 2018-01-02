package netty.webSocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * Created by Administrator on 2018/1/2.
 * Description: WebSocket 服务器
 */
public class WebSocketServer {
    public static void main(String[] args) {
            int port = 8080;
            if (args != null && args.length > 0) {
                try {
                    port = Integer.valueOf(args[0]);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        try {
            new WebSocketServer().run(port);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void run(int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    //将请求消息编码或者解码为HTTP消息
                                    .addLast("http-codec", new HttpServerCodec())
                                    //将HTTP消息的多个部分组合成完整的HTTP消息
                                    .addLast("http-aggregator", new HttpObjectAggregator(65536))
                                    //用来向客户端发送HTML5文件，支持浏览器和服务器进行WebSocket通信
                                    .addLast("http-chunked", new ChunkedWriteHandler())
                                    //WebSocket业务处理
                                    .addLast("handler", new WebSocketServerHandler());
                        }
                    });
            ChannelFuture future = bootstrap.bind(port).sync();
            System.out.println("Web socket server started at port " + port + ".");
            System.out.println("Open your browser and navigate to http://localhost:" + port + "/");
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
