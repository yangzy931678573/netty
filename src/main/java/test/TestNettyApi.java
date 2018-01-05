package test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.util.CharsetUtil;
import netty.jbossMarshalling.bean.SubscribeRequest;
import netty.proprietaryProtocol.codec.support.MarshallingCodecFactory;
import netty.proprietaryProtocol.codec.support.channelBuffer.MyChannelBufferByteInput;
import netty.proprietaryProtocol.codec.support.channelBuffer.MyChannelBufferByteOutput;
import netty.proprietaryProtocol.message.NettyMessage;
import org.jboss.marshalling.*;
import org.junit.Test;

import java.io.IOException;
import java.io.Serializable;
import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * Created by Administrator on 2017/12/29.
 * Description: 测试NettyApi的使用方法
 */

public class TestNettyApi implements Serializable{

    @Test
    public void testQueryStringDecoder(){
        QueryStringDecoder decoder = new QueryStringDecoder("");
    }
    @Test
    public void testByteBuf(){
        //从ByteBuf中读数据  getBytes()方法 意味着 get byte[] From ByteBuf to a new byte[]
        ByteBuf buffer = Unpooled.copiedBuffer("Yang  Yao",CharsetUtil.UTF_8);
        System.out.println(buffer.toString(CharsetUtil.UTF_8));//此时有数据
        byte[] bytes = new byte[buffer.readableBytes()];
        buffer.readBytes(bytes); //与get方法基本等价 意味着 read byte[] From ByteBuf to a new byte[]
       // buffer.getBytes(buffer.readerIndex(), bytes, 0, bytes.length);
        System.out.println(buffer.toString());//UnpooledHeapByteBuf(ridx: 0, widx: 14, cap: 14/14)
        System.out.println(buffer.toString(CharsetUtil.UTF_8));//显然buffer中的数据被读空了
        System.out.println(new String(bytes,CharsetUtil.UTF_8));//Yang  Yao

        //向ByteBuf中写数据  writeBytes()方法 意味着 write From byte[] to ByteBuf
        byte[] bits = "Yang  Yao".getBytes();
        ByteBuf buf = Unpooled.buffer();
        buf.writeBytes(bits);
        byte[] result = new byte[buf.readableBytes()];
        buf.getBytes(buf.readerIndex(), result, 0, result.length);
        System.out.println(new String(result));//Yang  Yao
    }
    @Test
    public void testMarshallingCodecFactory(){
        //这里ByteBuf的buffer显然脱离了不能自动扩容
        Marshaller marshaller = MarshallingCodecFactory.buildMarshaller();
        ByteBuf buf = Unpooled.buffer();
        NettyMessage message = new NettyMessage();
       // int lengthPos = buf.writerIndex();
       // buf.writeBytes(new byte[4]);//用来解码多个对象的操作
     /*   SubscribeRequest request = new SubscribeRequest();
        request.setRequestId(0);
        request.setUserName("Yang");
        request.setPhoneNumber("15738506750");
        request.setAddress("浙江省国家科技园");
        request.setProductName("Netty in action");*/
        try {
            MyChannelBufferByteOutput output = new MyChannelBufferByteOutput(buf);
            marshaller.start(output);
            marshaller.writeObject(message);
            System.out.println(message);
            marshaller.finish();
            //lengthPos = buf.writerIndex();
            marshaller.close();
            buf.setLong(0, 1L);//用来解码多个对象的操作
        } catch (IOException e) {
            e.printStackTrace();
        }
        Unmarshaller unmarshaller = MarshallingCodecFactory.buildUnmarshaller();
        try {
            MyChannelBufferByteInput input = new MyChannelBufferByteInput(buf);
            unmarshaller.start(input);
            Object api = unmarshaller.readObject();
            System.out.println(api);
            unmarshaller.finish();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testSign(){

        System.out.println(0 << 8 | 0 );
    }
}
