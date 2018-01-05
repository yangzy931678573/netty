package netty.webSocket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import static io.netty.handler.codec.http.HttpHeaders.isKeepAlive;

/**
 * Created by Administrator on 2018/1/2.
 * Description:业务处理器 ，这里泛型使用Object的原因是WebSocket连接会先发送一个HTTP请求来确认
 */
public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {

    private static final Logger logger = Logger.getLogger(WebSocketServerHandler.class.getName());
    //WebSocket协议握手
    private WebSocketServerHandshaker handshaker;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object o) throws Exception {
        //传统HTTP接入
        if (o instanceof FullHttpRequest) {
            handleHttpRequest(ctx, (FullHttpRequest) o);
            //WebSocket接入
        } else if (o instanceof WebSocketFrame) {
            handleWebSocketFrame(ctx, (WebSocketFrame) o);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
        //判断是否是关闭链路的指令
        if (frame instanceof CloseWebSocketFrame) {
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            return;
        }
        //判断是否是Ping消息
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        //本例只支持文本消息，不支持二进制消息
        if (!(frame instanceof TextWebSocketFrame)) {
            throw new UnsupportedOperationException(String.format("%s frame types not supported", frame.getClass().getName()));
        }
        String request = ((TextWebSocketFrame) frame).text();
        if (logger.isLoggable(Level.FINE)) {
            logger.fine(String.format("%s received %s", ctx.channel(), request));
        }

        // 问题在与System.in这个流是同一个流,对于不同请求并不能做出处.
        /*Scanner scanner = new Scanner(System.in);
        while (true) {
            String next = scanner.nextLine();//接收空格
            ctx.channel().writeAndFlush(new TextWebSocketFrame(next));//必须刷新
        }*/
        //callable接口的使用,没有必要!  Netty已经提供了线程组
           /* FutureTask<String> task = new FutureTask(new FutureMessage());
            new Thread(task).start();
            try {
                String result = task.get();
                ctx.channel().writeAndFlush(new TextWebSocketFrame(result));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
                ctx.channel().writeAndFlush(new
                        TextWebSocketFrame(request + "欢迎使用Netty WebSocket服务，现在时刻:" + new Date().toString()));
            }*/
        // 显然可以看出Netty WebSocket 显然是可以处理多个连接的
        ctx.channel().write(new
           TextWebSocketFrame(request + "欢迎使用Netty WebSocket服务，现在时刻:" + new Date().toString()));

    }

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest request) {
        if (!request.getDecoderResult().isSuccess() || !("webSocket").equalsIgnoreCase(request.headers().get("Upgrade"))) {
            sendHttpResponse(ctx, request, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }
        WebSocketServerHandshakerFactory factory =
                new WebSocketServerHandshakerFactory("ws://localhost:8080/webSocket", null, false);
        handshaker = factory.newHandshaker(request);
        if (handshaker == null)
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        else
            //这个方法动态的增加了WebSocketFrame解码编码器，因此可以直接得到WebSocketFrame对象
            handshaker.handshake(ctx.channel(), request);
    }

    private void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest request, DefaultFullHttpResponse response) {
        //返回应答到客户端
        if (response.getStatus().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(response.getStatus().toString(), CharsetUtil.UTF_8);
            response.content().writeBytes(buf);
            buf.release();//释放引用计数
            setContentLength(response, response.content().readableBytes());
        }
        //如果是非 Keep-Alive 连接，关闭连接
        ChannelFuture channelFuture = ctx.channel().writeAndFlush(response);
        if (isKeepAlive(request) || response.getStatus().code() != 200) {
            channelFuture.addListener(ChannelFutureListener.CLOSE);
        }
    }

    private void setContentLength(DefaultFullHttpResponse response, int length) {
        response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, length);
    }

  /*  private class FutureMessage implements Callable<String> {

        @Override
        public String call() throws Exception {
            Scanner scanner = new Scanner(System.in);
            String next = scanner.next();
            return next;
        }
    }*/
}
