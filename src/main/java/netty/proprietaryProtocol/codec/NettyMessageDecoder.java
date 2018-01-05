package netty.proprietaryProtocol.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.CharsetUtil;
import netty.proprietaryProtocol.codec.support.MarshallingDecoderSupport;
import netty.proprietaryProtocol.message.Header;
import netty.proprietaryProtocol.message.NettyMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/3.
 * Description: Netty协议栈消息解码器
 *   解码多个序列化对象的关键：（1）是在编码序列化对象时把每个对象的长度编码到ByteBuf对象中，
 *           （2）在解码时读取到该长度并按照一定格式截取ByteBuf对象，
 *           （3）从而得到每一个序列化对象的ByteBuf对象并进行解析
 */
public class NettyMessageDecoder extends LengthFieldBasedFrameDecoder {
    private MarshallingDecoderSupport support;

    public NettyMessageDecoder() {
        //为了防止单条消息过大导致内容溢出或者畸形码流导致解码错位引起内存分配失败,
        //需要对单条消息的最大长度进行限制
        super(1024 * 1024, 4,
                4, -8,
                0);
        // 注意这里由于我们编码时NettyMessage对象的长度被编码在对象里，变成ByteBuf对象，
        // 其可读字节就是整个NettyMessage对象,因此
        // 除了指明 对象长度 所在下标的漂移量lengthFieldOffset,所占的字节数组的长度lengthFieldLength 之外
        // 还需要设置lengthAdjustment 为 -(lengthFieldOffset+lengthFieldLength)
        // 这样父类在decode()方法中就会每次读取每个NettyMessage对象长度的字节数，
        // 从而解析出只包含每个NettyMessage对象的字节流并返回



        support = new MarshallingDecoderSupport();
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf frame = (ByteBuf) super.decode(ctx, in);
        //这里处理的是frame,而不是in
        //frame是经过解码处理的流，仅包含一个NettyMessage对象
        // 而in的readIndex已经在父类的decode()方法中被调整过，便于读取下一个对象
        if (frame == null)
            return null;

        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setCrcCode(frame.readInt());
        header.setLength(frame.readInt());
        header.setSessionId(frame.readLong());
        header.setType(frame.readByte());
        header.setPriority(frame.readByte());

        int size = frame.readInt();
        if (size > 0) {
            Map<String, Object> map = new HashMap<>(size);
            int keySize;
            byte[] keyArray;
            String key = null;
            for (int i = 0; i < size; i++) {
                keySize = frame.readInt();
                keyArray = new byte[keySize];
                frame.readBytes(keySize);
                key = new String(keyArray, CharsetUtil.UTF_8);
                map.put(key, support.decode(frame));
            }
            keyArray = null;
            key = null;
            header.setAttachment(map);
        }
        //如果可读取的字节数大于4,说明还有body
        if (frame.readableBytes() > 4)
            message.setBody(support.decode(frame));
        message.setHeader(header);

        return message;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
