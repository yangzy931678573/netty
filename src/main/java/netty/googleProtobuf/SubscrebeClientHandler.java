package netty.googleProtobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/27.
 * Description:
 */
public class SubscrebeClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i=0 ;i<1000;i++){
            ctx.write(sendRequest(i));
        }
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Client received server response : ");
        System.out.println(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
    private SubscribeRequestProto.SubscribeRequest sendRequest(int requestId){
        SubscribeRequestProto.SubscribeRequest.Builder builder = SubscribeRequestProto.SubscribeRequest.newBuilder();
        builder.setRequestId(requestId);
        builder.setUserName("Yang");
        List<String> list = new ArrayList<>();
        list.add("Beijing");
        list.add("Nanjing");
        list.add("Hangzhou");
        builder.addAllAddress(list);
        return builder.build();
    }
}
