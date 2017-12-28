package netty.jbossMarshalling;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.jbossMarshalling.bean.SubscribeRequest;
import netty.jbossMarshalling.bean.SubscribeResponse;

/**
 * Created by Administrator on 2017/12/27.
 * Description:
 */
public class MarshallSubscribeServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubscribeRequest request = (SubscribeRequest) msg;
        if ("Yang".equals(request.getUserName())) {
            System.out.println("Service accept a subscribe request : [" + request + "]");
            ctx.writeAndFlush(callResponse(request.getRequestId()));
        }
    }

    private SubscribeResponse callResponse(int requestId) {
        SubscribeResponse response = new SubscribeResponse();
        response.setRequestId(requestId);
        response.setResponseCode(0);
        response.setDescribe("Netty in action book order succeed , 3 day later , send to the designated address");
        return response;
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
