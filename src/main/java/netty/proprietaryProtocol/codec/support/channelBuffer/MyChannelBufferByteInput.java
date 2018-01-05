package netty.proprietaryProtocol.codec.support.channelBuffer;

import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.ByteInput;

import java.io.IOException;

/**
 * Created by Administrator on 2018/1/4.
 * Description:仿照Netty,自定义ChannelBufferByteInput类
 */
public class MyChannelBufferByteInput implements ByteInput {
    private final ByteBuf buffer;

    public MyChannelBufferByteInput(ByteBuf buffer) {
        this.buffer = buffer;
    }

    @Override
    public void close() throws IOException {
    }

    @Override
    public int available() throws IOException {
        return this.buffer.readableBytes();
    }

    @Override
    public int read() throws IOException {
        return this.buffer.isReadable() ? this.buffer.readByte() & 255 : -1;
    }

    @Override
    public int read(byte[] array) throws IOException {
        return this.read(array, 0, array.length);
    }

    @Override
    public int read(byte[] dst, int dstIndex, int length) throws IOException {
        int available = this.available();
        if (available == 0) {
            return -1;
        } else {
            length = Math.min(available, length);
            this.buffer.readBytes(dst, dstIndex, length);
            return length;
        }
    }
    //跳过指定字节
    @Override
    public long skip(long bytes) throws IOException {
        int readable = this.buffer.readableBytes();//当前可读字节
        if ((long) readable < bytes) {
            bytes = (long) readable;//如果指定字节大于当前可读字节，则重置需要跳过的字节，防止过度读取
        }
        //移动读取字节的下标
        this.buffer.readerIndex((int) ((long) this.buffer.readerIndex() + bytes));
        //返回跳过的字节
        return bytes;
    }
}
