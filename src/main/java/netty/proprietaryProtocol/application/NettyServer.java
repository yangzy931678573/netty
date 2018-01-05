package netty.proprietaryProtocol.application;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import netty.proprietaryProtocol.codec.NettyMessageDecoder;
import netty.proprietaryProtocol.codec.NettyMessageEncoder;
import netty.proprietaryProtocol.constants.NettyConstant;
import netty.proprietaryProtocol.handler.HeartBeatRespHandler;
import netty.proprietaryProtocol.handler.LoginAuthRespHandler;


/**
 * Created by Administrator on 2018/1/5.
 * Description: Netty协议栈服务端
 */
public class NettyServer {
    public void bind() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                //用来配置管道和工场
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 100)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        channel.pipeline()
                                .addLast("MessageDecoder", new NettyMessageDecoder())
                                .addLast("MessageEncoder", new NettyMessageEncoder())
                                //Netty的ReadTimeoutHandler机制,默认50s没有读取到对方的任何消息时会主动关闭链路
                                .addLast("ReadTimeoutHandler", new ReadTimeoutHandler(50))
                                .addLast(new LoginAuthRespHandler())
                                .addLast("HeartBeatHandler", new HeartBeatRespHandler());
                    }
                });
        bootstrap.bind(NettyConstant.REMOTE_IP, NettyConstant.PORT).sync();
        System.out.println("Netty server start OK : " + NettyConstant.REMOTE_IP + " : " + NettyConstant.PORT);
    }
}
