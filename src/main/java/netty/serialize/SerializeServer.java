package netty.serialize;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Created by Administrator on 2017/12/27.
 * Description:Java 序列化服务器
 */
public class SerializeServer {

    public static void main(String[] args) {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        new SerializeServer().bind(port);
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
                                    //1024 * 1024这是单个对象序列化后的字节数组的最大长度
                                    .addLast(new ObjectDecoder(1024 * 1024,
                                            //这里使用weakCachingConcurrentResolver创建的线程安全的WeakReferenceMap对类加载进行缓存，
                                            // 支持多线程并发访问，当虚拟机内存不足时，会释放缓存中的内存，防止内存泄漏
                                            ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())))
                                    .addLast(new ObjectEncoder())
                                    .addLast(new SerializeServerHandler());
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
