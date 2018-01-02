package netty.xmlhttp.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * Created by Administrator on 2018/1/2.
 * Description:
 */
public class AbstractHttpXmlEncoder<T> extends MessageToMessageEncoder<T> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, T t, List<Object> list) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
