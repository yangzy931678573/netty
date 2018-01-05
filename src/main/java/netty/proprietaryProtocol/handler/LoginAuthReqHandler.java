package netty.proprietaryProtocol.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.proprietaryProtocol.constants.MessageType;
import netty.proprietaryProtocol.message.Header;
import netty.proprietaryProtocol.message.NettyMessage;

/**
 * Created by Administrator on 2018/1/5.
 * Description:  握手认证客户端
 */
public class LoginAuthReqHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //主动发送握手请求
        ctx.writeAndFlush(buildLoginReq());
    }
    private Object buildLoginReq() {
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.LOGIN_REQ.value());
        message.setHeader(header);
        return message;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage) msg;
        //判断是否登录成功
        if (message.getHeader() != null &&
                message.getHeader().getType() == MessageType.LOGIN_RESP.value()) {
            byte loginResult = (byte) message.getBody();
            if (loginResult != (byte) 0) {
                ctx.close();
            } else {
                System.out.println("Login is OK : " + message);
                ctx.fireChannelRead(msg);
            }
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.fireExceptionCaught(cause);
    }
}
