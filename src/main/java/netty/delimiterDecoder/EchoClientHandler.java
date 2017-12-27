package netty.delimiterDecoder;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.logging.Logger;

/**
 * Created by Administrator on 2017/12/26.
 * Description:
 */
@ChannelHandler.Sharable
public class EchoClientHandler extends ChannelInboundHandlerAdapter {
    private final Logger logger = Logger.getLogger(EchoClient.class.getName());
    //在构造函数中也可以初始化 final常量

    private int counter;

    static final String ECHO_REQ = "Hi , Yang . Welcome to Netty.$_";

    public EchoClientHandler() {
    }

    //管道处于激活状态，可以发送信息
    @Override
    public void channelActive(ChannelHandlerContext context) throws Exception {
        for (int i = 0; i < 10; i++) {
            context.writeAndFlush(Unpooled.copiedBuffer(ECHO_REQ.getBytes()));
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext context, Object msg) throws Exception {
        //自定义分隔符解码器
        System.out.println("This is :" + ++counter + " times receive server : [" + msg + "]");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) throws Exception {
        logger.warning("Unexpected exception from downstream : " + cause.getMessage());
        cause.printStackTrace();
        context.close();
    }
}
