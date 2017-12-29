package test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.QueryStringDecoder;
import org.junit.Test;

/**
 * Created by Administrator on 2017/12/29.
 * Description: 测试NettyApi的使用方法
 */

public class TestNettyApi {

    @Test
    public void testQueryStringDecoder(){
        QueryStringDecoder decoder = new QueryStringDecoder("");
    }
    @Test
    public void testByteBuf(){
        //从ByteBuf中读数据  getBytes()方法 意味着 get byte[] From ByteBuf to a new byte[]
        ByteBuf buffer = Unpooled.copiedBuffer("Yang  Yao".getBytes());
        byte[] bytes = new byte[buffer.readableBytes()];
        buffer.getBytes(buffer.readerIndex(), bytes, 0, bytes.length);
        System.out.println(buffer.toString());//UnpooledHeapByteBuf(ridx: 0, widx: 14, cap: 14/14)
        System.out.println(new String(bytes));//Yang  Yao

        //向ByteBuf中写数据  writeBytes()方法 意味着 write From byte[]
        byte[] bits = "Yang  Yao".getBytes();
        ByteBuf buf = Unpooled.buffer();
        buf.writeBytes(bits);
        byte[] result = new byte[buf.readableBytes()];
        buf.getBytes(buf.readerIndex(), result, 0, result.length);
        System.out.println(new String(result));//Yang  Yao
    }
}
