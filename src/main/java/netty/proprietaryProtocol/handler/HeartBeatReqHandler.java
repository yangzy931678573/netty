package netty.proprietaryProtocol.handler;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.proprietaryProtocol.constants.MessageType;
import netty.proprietaryProtocol.message.Header;
import netty.proprietaryProtocol.message.NettyMessage;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018/1/5.
 * Description: 心跳检测请求处理器
 */
public class HeartBeatReqHandler extends ChannelInboundHandlerAdapter {
    private volatile ScheduledFuture<?> heartBeatTask;


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage) msg;
        //握手成功，主动发送心跳消息
        if (message.getHeader() != null &&
                message.getHeader().getType() == MessageType.LOGIN_RESP.value()) {
            heartBeatTask = ctx.executor().scheduleAtFixedRate(
                    new HeartBeatReqHandler.HeartBeatTask(ctx),
                    0, 5000, TimeUnit.MILLISECONDS);
        } else if (message.getHeader() != null &&
                message.getHeader().getType() == MessageType.HEARTBEAT_RESP.value()) {
            System.out.println("Client receive server heart beat message : ---> " + message);
        } else {
            ctx.fireChannelRead(message);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //发生异常关闭定时任务
        if (heartBeatTask != null) {
            heartBeatTask.cancel(true);
            heartBeatTask = null;
        }
        cause.printStackTrace();
        ctx.fireExceptionCaught(cause);

    }

    private class HeartBeatTask implements Runnable {
        private final ChannelHandlerContext ctx;

        public HeartBeatTask(ChannelHandlerContext ctx) {
            this.ctx = ctx;
        }

        @Override
        public void run() {
            NettyMessage heartBeat = buildHeartBeat();
            ctx.writeAndFlush(heartBeat)
                    //这里发生连接异常并没有被捕获,因此需要设置一个监听器,当服务端关闭时,主动释放心跳定时器
                    .addListener((channelFuture) -> {
                                if (!channelFuture.isSuccess()) {
                                    if (heartBeatTask != null) {
                                        heartBeatTask.cancel(true);
                                        heartBeatTask = null;
                                    }
                                }
                            }
                    );
            System.out.println("Client send heart beat message to server : --->" + heartBeat);
        }

        private NettyMessage buildHeartBeat() {
            NettyMessage message = new NettyMessage();
            Header header = new Header();
            header.setType(MessageType.HEARTBEAT_REQ.value());
            message.setHeader(header);
            return message;
        }
    }
}
