package netty.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Administrator on 2018/1/3.
 * Description: UDP 服务端
 */
public class UdpServer {
    public static void main(String[] args) {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {

            }
        }
        try {
            new UdpServer().bind(port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void bind(int port) throws Exception {
        //配置NIO线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(bossGroup)
                    .channel(NioDatagramChannel.class)//配置管道类
                    .option(ChannelOption.SO_BROADCAST, true)//配置管道的参数
                    .handler(new ChineseProverServerHandler());//抽象类不能使用lambda表达式
            bootstrap.bind(port).sync().channel().closeFuture().await();
        } finally {
            bossGroup.shutdownGracefully();
        }
    }
    private class ChineseProverServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {
        private final String[] DICTIONARY = {"老骥伏枥", "志在千里", "烈士暮年", "壮心不已"};

        private String nextQuote() {
            //由于存在多线程并发操作的可能，所以使用线程安全随机类ThreadLocalRandom
            int quoteId = ThreadLocalRandom.current().nextInt(DICTIONARY.length);
            return DICTIONARY[quoteId];
        }

        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket) throws Exception {
            String req = datagramPacket.content().toString(CharsetUtil.UTF_8);
            System.out.println(req);
            if ("龟虽寿".equals(req)) {
                channelHandlerContext.writeAndFlush(
                        new DatagramPacket(
                                Unpooled.copiedBuffer("查询结果 ： " + nextQuote(), CharsetUtil.UTF_8),
                                datagramPacket.sender()));//发送UDP报文的IP地址+端口号
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            ctx.close();
            cause.printStackTrace();
        }
    }
}
