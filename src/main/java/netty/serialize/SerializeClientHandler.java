package netty.serialize;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.serialize.bean.SubscribeRequest;

/**
 * Created by Administrator on 2017/12/27.
 * Description:
 */
public class SerializeClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
            ctx.write(sendRequest(i));
        }
        ctx.flush();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Receive service response : [" + msg + "]");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private SubscribeRequest sendRequest(int requestId) {
        SubscribeRequest request = new SubscribeRequest();
        request.setRequestId(requestId);
        request.setUserName("Yang");
        request.setPhoneNumber("15738506750");
        request.setAddress("浙江省国家科技园");
        request.setProductName("Netty权威指南");
        return request;
    }
}
