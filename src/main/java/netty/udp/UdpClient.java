package netty.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

/**
 * Created by Administrator on 2018/1/3.
 * Description:UDP客户端
 */
public class UdpClient {
    public void bind(int port) throws Exception {
        //配置NIO线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(bossGroup)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new ChineseQueryClientHandler());

            Channel channel = bootstrap.bind(0).sync().channel();
            //向网段内所有机器广播UDP消息
            channel.writeAndFlush(new DatagramPacket(
                    Unpooled.copiedBuffer("龟虽寿", CharsetUtil.UTF_8),
                    new InetSocketAddress("255.255.255.255", port))).sync();
            if (!channel.closeFuture().await(15000))
                System.out.println("查询超时");
        } finally {
            bossGroup.shutdownGracefully();
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
            new UdpClient().bind(port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class ChineseQueryClientHandler extends SimpleChannelInboundHandler<DatagramPacket> {

        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket) throws Exception {
            String resp = datagramPacket.content().toString(CharsetUtil.UTF_8);
            if (resp.startsWith("查询结果")) {
                System.out.println(resp);
                channelHandlerContext.close();
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            ctx.close();
            cause.printStackTrace();
        }
    }
}
