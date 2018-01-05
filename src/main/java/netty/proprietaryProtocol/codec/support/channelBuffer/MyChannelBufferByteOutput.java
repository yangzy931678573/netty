package netty.proprietaryProtocol.codec.support.channelBuffer;

import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.ByteOutput;

import java.io.IOException;

/**
 * Created by Administrator on 2018/1/4.
 * Description: 仿照Netty,自定义ChannelBufferByteOutput类
 */
public class MyChannelBufferByteOutput implements ByteOutput {
    private final ByteBuf byteBuf;

    public MyChannelBufferByteOutput(ByteBuf byteBuf){
        this.byteBuf = byteBuf;
    }
    @Override
    public void close() throws IOException {
       //标记接口意味着不用做任何处理
    }
    @Override
    public void flush() throws IOException {
        //标记接口意味着不用做任何处理
    }
    @Override
    public void write(int b) throws IOException {
        this.byteBuf.writeByte(b);
    }
    @Override
    public void write(byte[] bytes) throws IOException {
        this.byteBuf.writeBytes(bytes);
    }
    @Override
    public void write(byte[] bytes, int srcIndex, int length) throws IOException {
        this.byteBuf.writeBytes(bytes, srcIndex, length);
    }

    public ByteBuf getBuffer() {
        return this.byteBuf;
    }
}
