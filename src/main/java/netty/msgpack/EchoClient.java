package netty.msgpack;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;


/**
 * Created by Administrator on 2017/12/25.
 * Description: msg pack客户端
 */
public class EchoClient {
    public static void main(String[] args) {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {

            }
        }
        try {
            new EchoClient("127.0.0.1", port, 1000).run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final String host;
    private final int port;
    private final int sendNumber;

    public EchoClient(String host, int port, int sendNumber) {
        this.host = host;
        this.port = port;
        this.sendNumber = sendNumber;
    }

    private void run() throws Exception {
        //配置客户端NIO线程组
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            //核心代码
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(
                                            new LengthFieldBasedFrameDecoder(
                                                    65535,0,
                                                    2,0,
                                                    2))
                                    .addLast(new MsgpackDecoder())
                                    .addLast(new LengthFieldPrepender(2))
                                    .addLast(new MsgpackEncoder())
                                    .addLast(new EchoClientHandler(sendNumber));
                        }
                    });

            //发起异步连接操作
            ChannelFuture future = bootstrap.connect(this.host, this.port).sync();
            //等待客户端链路关闭
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }


}
