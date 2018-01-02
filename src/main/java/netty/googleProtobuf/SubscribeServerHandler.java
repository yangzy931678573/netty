package netty.googleProtobuf;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by Administrator on 2017/12/27.
 * Description:
 */
public class SubscribeServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubscribeRequestProto.SubscribeRequest request = (SubscribeRequestProto.SubscribeRequest) msg;
        if (request.getUserName().equals("Yang")) {
            System.out.println("Service accept client subscribe request : ");
            System.out.println(request.toString());
            ctx.writeAndFlush(sendResponse(request.getRequestId()));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private SubscribeResponseProto.SubscribeResponse sendResponse(int requestId) {
        SubscribeResponseProto.SubscribeResponse.Builder builder = SubscribeResponseProto.SubscribeResponse.newBuilder();
        builder.setRequestId(requestId);
        builder.setDecribe("Netty book order succeed , 3 days later , will send to designed address");
        builder.setRespCode("0");
        return builder.build();
    }
}
