package netty.proprietaryProtocol.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import netty.proprietaryProtocol.codec.support.MarshallingEncoderSupport;
import netty.proprietaryProtocol.message.NettyMessage;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/3.
 * Description: Netty协议栈消息编码器
 */
public final class NettyMessageEncoder extends MessageToMessageEncoder<NettyMessage> {
    MarshallingEncoderSupport encoder;

    public NettyMessageEncoder() {
        encoder = new MarshallingEncoderSupport();
    }

    //这里不需要操作channelHandlerContext对象,父类已经有了实现
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, NettyMessage nettyMessage, List<Object> list) throws Exception {
        if (nettyMessage == null || nettyMessage.getHeader() == null)
            throw new RuntimeException("Unexpected null message exception");
        ByteBuf send = Unpooled.buffer();
        //编码按照特定顺序 write from Object to ByteBuf
        send.writeInt(nettyMessage.getHeader().getCrcCode());
        //先写入Header的长度站位,便于后边设置长度
        send.writeInt(nettyMessage.getHeader().getLength());
        send.writeLong(nettyMessage.getHeader().getSessionId());
        send.writeByte(nettyMessage.getHeader().getType());
        send.writeByte(nettyMessage.getHeader().getPriority());
        send.writeInt(nettyMessage.getHeader().getAttachment().size());//attachment不能为空
        String key;
        byte[] keyArray;
        Object value;
        for (Map.Entry<String, Object> objectEntry : nettyMessage.getHeader().getAttachment().entrySet()) {
            key = objectEntry.getKey();
            keyArray = key.getBytes("UTF-8");
            send.writeInt(keyArray.length);
            send.writeBytes(keyArray);
            value = objectEntry.getValue();
            encoder.encode(value, send);
        }
        //便于控制GC ?
        key = null;
        keyArray = null;
        value = null;
        if (nettyMessage.getBody() != null)
            encoder.encode(nettyMessage.getBody(), send);
        else
            send.writeInt(0);
        // 跳过的crcCode的4个字节,在第5个字节处写入Buffer的可读字节长度,替换掉原来的Header.length
        // setInt以为着会替换5到8这几个字节
        send.setInt(4, send.readableBytes());
    }
}
