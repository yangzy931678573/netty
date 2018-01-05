package netty.proprietaryProtocol.codec.support;

import org.jboss.marshalling.*;

import java.io.IOException;

/**
 * Created by Administrator on 2018/1/4.
 * Description: Marshalling 编解码器静态工场
 */
public class MarshallingCodecFactory {
    private static final MarshallerFactory factory;
    private static final MarshallingConfiguration configuration;

    private MarshallingCodecFactory(){}
    static {
        /**
         *  序列化工厂 支持配置版本为5 默认头为[-84,-19]
         *
         */
        /*
        factory = Marshalling.getProvidedMarshallerFactory("serial");
        configuration = new MarshallingConfiguration();
        configuration.setVersion(5);*/
        /**
         *  river工厂 支持配置版本为2~4
         *
         */
        factory = Marshalling.getProvidedMarshallerFactory("river");
        configuration = new MarshallingConfiguration();
        configuration.setVersion(3);


    }
    //有可能成为性能瓶颈？

    public static Marshaller buildMarshaller() {
        try {
            return factory.createMarshaller(configuration);
        } catch (IOException e) {
            System.out.println("Unexpected I/O exception");
            e.printStackTrace();
            return null;
        }
    }

    public static Unmarshaller buildUnmarshaller() {
        try {
            return factory.createUnmarshaller(configuration);
        } catch (IOException e) {
            System.out.println("Unexpected I/O exception");
            e.printStackTrace();
            return null;
        }
    }
}
