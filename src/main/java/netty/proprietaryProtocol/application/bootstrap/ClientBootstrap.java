package netty.proprietaryProtocol.application.bootstrap;

import netty.proprietaryProtocol.application.NettyClient;
import netty.proprietaryProtocol.constants.NettyConstant;

/**
 * Created by Administrator on 2018/1/5.
 * Description: 客户端启动器
 */
public class ClientBootstrap {
    public static void main(String[] args) {
        try {
            new NettyClient().connect(NettyConstant.PORT, NettyConstant.REMOTE_IP);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
