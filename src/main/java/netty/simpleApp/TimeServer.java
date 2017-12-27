package netty.simpleApp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.util.Date;


/**
 * Created by Administrator on 2017/12/25.
 * Description: NIO 时间服务器
 */
public class TimeServer {
    public void bind(int port) throws Exception {
        //配置NIO线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(bossGroup, workerGroup)//配置主线程  和工作线程
                    .channel(NioServerSocketChannel.class)//配置管道类
                    .option(ChannelOption.SO_BACKLOG, 1024)//配置管道的参数
                    .childHandler(new ChildChannelHandler());//抽象类不能使用lambda表达式
            //绑定端口，同步等待成功
            ChannelFuture future = bootstrap.bind(port).sync();
            //等待服务监听端口关闭
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {

            }
        }
        try {
            new TimeServer().bind(port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //管道处理器
    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
        //initChannel是唯一需要重写的方法，这里可以配置多个数据处理器
        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            socketChannel.pipeline()
                    .addLast(new LineBasedFrameDecoder(1024))
                    .addLast(new StringDecoder())
                    .addLast(new TimeServerHandler());
            //socketChannel.pipeline().addLast(new TimeServerHandler());未使用Netty的解码器

        }
    }

    //用于数据交互的处理器
    private class TimeServerHandler extends ChannelInboundHandlerAdapter {
        private int counter;

        @Override
        public void channelRead(ChannelHandlerContext context, Object message) throws Exception {


            /*未使用Netty的解码器
            ByteBuf buf = (ByteBuf) message;
            byte[] req = new byte[buf.readableBytes()];
            buf.readBytes(req);
            String body = new String(req, StandardCharsets.UTF_8)
                    .substring(0, req.length - System.getProperty("line.separator").
                            length());*/
            //分隔换行符解码器
            String body = (String) message;
            System.out.println("The time server receive order : " + body
                    + " ; the counter is : " + ++counter);
            String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ?
                    new Date(System.currentTimeMillis()).toString()
                    : "BAD QUERY";
            currentTime += System.getProperty("line.separator");
            ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
            context.writeAndFlush(resp);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext context, Throwable cause) throws Exception {
            cause.printStackTrace();
            context.close();
        }
    }
}