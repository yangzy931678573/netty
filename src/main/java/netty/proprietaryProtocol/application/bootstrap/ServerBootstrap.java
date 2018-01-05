package netty.proprietaryProtocol.application.bootstrap;

import netty.proprietaryProtocol.application.NettyServer;

/**
 * Created by Administrator on 2018/1/5.
 * Description:
 */
public class ServerBootstrap {
    public static void main(String[] args) {
        try {
            new NettyServer().bind();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
