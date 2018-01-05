package netty.proprietaryProtocol.codec.support;

import io.netty.buffer.ByteBuf;
import netty.proprietaryProtocol.codec.support.channelBuffer.MyChannelBufferByteOutput;
import org.jboss.marshalling.ByteBufferOutput;
import org.jboss.marshalling.Marshaller;

import java.io.IOException;

/**
 * Created by Administrator on 2018/1/4.
 * Description: NettyMessage编码器 的辅助类
 */
public class MarshallingEncoderSupport {
    private static final byte[] LENGTH_PLACEHOLDER = new byte[4];//长度占位符,编码多个对象时的起始字节
    Marshaller marshaller;

    public MarshallingEncoderSupport() {
        marshaller = MarshallingCodecFactory.buildMarshaller();
    }

    public void encode(Object value, ByteBuf outBuf) {

        try {
            int lengthPos = outBuf.writerIndex();
            outBuf.writeBytes(LENGTH_PLACEHOLDER);

            MyChannelBufferByteOutput output = new MyChannelBufferByteOutput(outBuf);
            marshaller.start(output);
            marshaller.writeObject(value);
            marshaller.finish();
            //设置当前对象的size字节,outBuf.writerIndex()-lengthPos-4即为读取的对象的字节数
            outBuf.setInt(lengthPos, outBuf.writerIndex() - lengthPos - 4);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                marshaller.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
