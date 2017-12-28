package netty.googleProtobuf.test;

import com.google.protobuf.InvalidProtocolBufferException;
import netty.googleProtobuf.SubscribeRequestProto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/27.
 * Description: 编解码开发测试
 */
public class TestSubscribeRequestProto {
    public static void main(String[] args) throws InvalidProtocolBufferException {
        SubscribeRequestProto.SubscribeRequest subscribeRequest = createSubscribeRequest();
        System.out.println("Before encode : " + subscribeRequest.toString());
        SubscribeRequestProto.SubscribeRequest decodeSubscribeRequest = decode(encode(subscribeRequest));
        System.out.println("After encode and decode : " + subscribeRequest.toString());
        System.out.println("Assert equal : " + decodeSubscribeRequest.equals(subscribeRequest));
    }
    private static byte[] encode(SubscribeRequestProto.SubscribeRequest request) {

        return request.toByteArray();
    }

    private static SubscribeRequestProto.SubscribeRequest decode(byte[] bytes) throws InvalidProtocolBufferException {
        return SubscribeRequestProto.SubscribeRequest.parseFrom(bytes);
    }
    private static SubscribeRequestProto.SubscribeRequest createSubscribeRequest(){
        SubscribeRequestProto.SubscribeRequest.Builder builder = SubscribeRequestProto.SubscribeRequest.newBuilder();
        builder.setRequestId(1);
        builder.setUserName("Yang");
        List<String> list = new ArrayList<>();
        list.add("Beijing");
        list.add("Nanjing");
        list.add("Hangzhou");
        builder.addAllAddress(list);
        return builder.build();
    }
}
