package netty.proprietaryProtocol.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.CharsetUtil;
import netty.proprietaryProtocol.codec.support.MarshallingDecoderSupport;
import netty.proprietaryProtocol.message.Header;
import netty.proprietaryProtocol.message.NettyMessage;

import javax.xml.bind.Marshaller;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/3.
 * Description: Netty协议栈消息解码器
 */
public class NettyMessageDecoder extends LengthFieldBasedFrameDecoder {
    private MarshallingDecoderSupport support;

    public NettyMessageDecoder(int maxObjectSize) {
        super(maxObjectSize, 0, 4, 0, 4);
        support = new MarshallingDecoderSupport();
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf frame = (ByteBuf) super.decode(ctx, in);
        if (frame == null)
            return null;
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setCrcCode(in.readInt());
        header.setLength(in.readInt());
        header.setSessionId(in.readLong());
        header.setType(in.readByte());
        header.setPriority(in.readByte());

        int size = in.readInt();
        if (size > 0) {
            Map<String, Object> map = new HashMap<>(size);
            int keySize;
            byte[] keyArray;
            String key = null;
            for (int i = 0; i < size; i++) {
                keySize = in.readInt();
                keyArray = new byte[keySize];
                in.readBytes(keySize);
                key = new String(keyArray, CharsetUtil.UTF_8);
                map.put(key, support.decode(in));
            }
            keyArray = null;
            key = null;
            header.setAttachment(map);
        }
        //如果可读取的字节数大于4,说明还有body
        if (in.readableBytes() > 4)
            message.setBody(support.decode(in));
        message.setHeader(header);
        return message;
    }
}
