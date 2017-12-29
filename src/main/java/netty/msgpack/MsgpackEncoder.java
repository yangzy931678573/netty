package netty.msgpack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;


/**
 * Created by Administrator on 2017/12/27.
 * Description: MessagePack 编码器：把Object对象转换成ByteBuf对象
 */
public class MsgpackEncoder extends MessageToByteEncoder<Object> {


    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf byteBuf) throws Exception {
        MessagePack messagePack = new MessagePack();
        //需要指定一个类型
        messagePack.register(UserInfo.class);

        byte[] raw = messagePack.write(msg);
        byteBuf.writeBytes(raw);
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
