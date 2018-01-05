package netty.proprietaryProtocol.application;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import netty.proprietaryProtocol.codec.NettyMessageDecoder;
import netty.proprietaryProtocol.codec.NettyMessageEncoder;
import netty.proprietaryProtocol.constants.NettyConstant;
import netty.proprietaryProtocol.handler.HeartBeatReqHandler;
import netty.proprietaryProtocol.handler.LoginAuthReqHandler;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018/1/5.
 * Description: Netty协议栈客户端
 */
public class NettyClient {
    //定时线程池
    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    EventLoopGroup group = new NioEventLoopGroup();

    public void connect(int port, String host) throws InterruptedException {
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(this.group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast("MessageDecoder", new NettyMessageDecoder())
                                    .addLast("MessageEncoder",new NettyMessageEncoder())
                                    //Netty的ReadTimeoutHandler机制,默认50s没有读取到对方的任何消息时会主动关闭链路
                                    .addLast("ReadTimeoutHandler",new ReadTimeoutHandler(50))
                                    .addLast("LoginAuthHandler",new LoginAuthReqHandler())
                                    .addLast("HeartBeatHandler",new HeartBeatReqHandler());
                        }
                    });
            //发起异步连接操作
            ChannelFuture future = bootstrap.connect(new InetSocketAddress(host, port),
                    new InetSocketAddress(NettyConstant.LOCAL_IP, NettyConstant.LOCAL_PORT)).sync();
            future.channel().closeFuture().sync();
        } finally {
            //所有资源释放完成后,清空资源,再次发起重连操作
            executor.execute(() ->{
                try {
                    TimeUnit.SECONDS.sleep(5);
                    connect(NettyConstant.PORT,NettyConstant.REMOTE_IP);//发起重连操作
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
