package netty.jbossMarshalling;

import io.netty.handler.codec.marshalling.*;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

/**
 * Created by Administrator on 2017/12/27.
 * Description: 创建JBoss Marshallling 编解码器工厂
 */
public class MarshallingCodeFactory {
    //解码器
    public static MarshallingDecoder buildMarshallingDecoder() {

        final MarshallerFactory factory = Marshalling.getProvidedMarshallerFactory("serial");
        //MarshallerFactory factory = null;
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        UnmarshallerProvider provider = new DefaultUnmarshallerProvider(factory, configuration);
        MarshallingDecoder decoder = new MarshallingDecoder(provider, 1024);

        return decoder;
    }

    //编码器
    public static MarshallingEncoder buildMarshallingEncoder() {
        final MarshallerFactory factory = Marshalling.getProvidedMarshallerFactory("serial");//支持Java序列化的工场
        // MarshallerFactory factory = null;控制台未打印错误？
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        MarshallerProvider provider = new DefaultMarshallerProvider(factory, configuration);
        MarshallingEncoder encoder = new MarshallingEncoder(provider);
        return encoder;
    }
}
