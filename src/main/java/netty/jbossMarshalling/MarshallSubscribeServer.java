package netty.jbossMarshalling;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Created by Administrator on 2017/12/27.
 * Description:Java 序列化服务器
 */
public class MarshallSubscribeServer {

    public static void main(String[] args) {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        new MarshallSubscribeServer().bind(port);
    }

    public void bind(int port) {
        NioEventLoopGroup serverGroup = new NioEventLoopGroup();
        NioEventLoopGroup clientGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(serverGroup, clientGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)//配置缓冲区
                    .handler(new LoggingHandler(LogLevel.INFO))//配置日志
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //对管道增加对象解码器
                            socketChannel.pipeline()
                                    .addLast(MarshallingCodeFactory.buildMarshallingDecoder())
                                    .addLast(MarshallingCodeFactory.buildMarshallingEncoder())
                                    .addLast(new MarshallSubscribeServerHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {

        }

    }
}
