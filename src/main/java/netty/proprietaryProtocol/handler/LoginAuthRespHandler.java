package netty.proprietaryProtocol.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.proprietaryProtocol.constants.MessageType;
import netty.proprietaryProtocol.message.Header;
import netty.proprietaryProtocol.message.NettyMessage;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2018/1/5.
 * Description: 握手应答处理器
 */
public class LoginAuthRespHandler extends ChannelInboundHandlerAdapter {
    private Map<String, Boolean> nodeCheck = new ConcurrentHashMap<>();
    private String[] whiteList = {"127.0.0.1", "192.168.1.113"};

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage) msg;
        //判断是否是握手请求
        if (message.getHeader() != null
                && message.getHeader().getType() == MessageType.LOGIN_REQ.value()) {
            String nodeIndex = ctx.channel().remoteAddress().toString();
            NettyMessage loginResp;
            //检查缓存中是否包含该节点,防止重复登录占用过多句柄资源
            if (nodeCheck.containsKey(nodeIndex)) {
                loginResp = buildResponse((byte) -1);
            } else {
                InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
                String ip = address.getAddress().getHostAddress();
                boolean isOK = false;
                //判断是否是白名单的ip地址
                for (String whiteIp : whiteList) {
                    if (whiteIp.equals(ip)) {
                        isOK = true;
                        break;
                    }
                }
                loginResp = isOK ? buildResponse((byte) 0) :
                        buildResponse((byte) -1);
                if (isOK)
                    nodeCheck.put(nodeIndex, true);
            }
            System.out.println("The login response is : " +
                    loginResp + "body [" +
                    loginResp.getBody() + "]");
            ctx.writeAndFlush(loginResp);
        } else {
            ctx.fireChannelRead(msg);
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //当发生异常关闭链路时,需要将客户端的信息从登录注册表中去注册,确保客户端可以重连
        nodeCheck.remove(ctx.channel().remoteAddress().toString());
        ctx.close();
        cause.printStackTrace();
        ctx.fireExceptionCaught(cause);
    }

    private NettyMessage buildResponse(byte result) {
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.LOGIN_RESP.value());
        message.setHeader(header);
        message.setBody(result);
        return message;
    }
}
