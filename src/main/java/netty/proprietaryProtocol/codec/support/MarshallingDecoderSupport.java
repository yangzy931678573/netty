package netty.proprietaryProtocol.codec.support;


import io.netty.buffer.ByteBuf;
import netty.proprietaryProtocol.codec.support.channelBuffer.MyChannelBufferByteInput;
import org.jboss.marshalling.ByteInput;
import org.jboss.marshalling.Unmarshaller;

import java.io.IOException;

/**
 * Created by Administrator on 2018/1/4.
 * Description: NettyMessage 解码器辅助类
 */
public class MarshallingDecoderSupport {
    private final Unmarshaller unmarshaller;

    public MarshallingDecoderSupport() {
        unmarshaller = MarshallingCodecFactory.buildUnmarshaller();
    }

    public Object decode(ByteBuf in) {
        //这里的in已经被LengthFieldBasedFrameDecoder的decode方法去除了长度占位符数组

        //获取对象的字节长度,与MarshallingEncoderSupport的encode()的最后一步相对应
        int objectSize = in.readInt();
        ByteBuf buf = in.slice(in.readerIndex(), objectSize);//从当前可读下标到对象长度,切割ByteBuf为一个对象的长度

        ByteInput input = new MyChannelBufferByteInput(buf);

        try {
            unmarshaller.start(input);
            Object object = unmarshaller.readObject();
            unmarshaller.finish();

            //重置读取的起始坐标,跳过当前已读下标和对象的长度,从而获取下一个对象
            in.readerIndex(in.readerIndex() + objectSize);

            return object;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                unmarshaller.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
