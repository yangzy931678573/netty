package netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedFile;
import io.netty.util.CharsetUtil;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Pattern;

import static io.netty.handler.codec.http.HttpHeaders.Names.*;
import static io.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static io.netty.handler.codec.http.HttpResponseStatus.*;

/**
 * Created by Administrator on 2017/12/28.
 * Description:
 */
public class HttpFileServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private String root;


    public HttpFileServerHandler(String root) {
        this.root = root;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
      /*  HttpPostRequestDecoder httpPostRequestDecoder = new HttpPostRequestDecoder(new DefaultHttpDataFactory(), request, CharsetUtil.UTF_8);
        List<InterfaceHttpData> bodyHttpDatas = httpPostRequestDecoder.getBodyHttpDatas();
        for (InterfaceHttpData bodyHttpData : bodyHttpDatas) {
            if (bodyHttpData.getHttpDataType() == InterfaceHttpData.HttpDataType.Attribute) {
                Attribute attribute = (Attribute) bodyHttpData;
                System.out.println(attribute.getName() + ":" + attribute.getValue());
            }
        }*/
       /* ByteBuf content = request.content();
        final int len = content.readableBytes();
        final byte[] a = new byte[len];
        content.getBytes(content.readerIndex(),a,0,len);
        QueryStringDecoder decoder = new QueryStringDecoder(new String(a),CharsetUtil.UTF_8,false);
        Map<String, List<String>> parameters = decoder.parameters();
        for (Map.Entry<String, List<String>> entry : parameters.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }*/

        if (!request.getDecoderResult().isSuccess()) {
            sendError(ctx, BAD_REQUEST);
            return;
        }

        if (request.getMethod() != HttpMethod.GET) {
            sendError(ctx, METHOD_NOT_ALLOWED);
            return;
        }
        final String uri = request.getUri();
        //根据请求的uri来获取对应的文件路径
        final String path = sanitizeUri(uri);
        if (path == null) {
            sendError(ctx, FORBIDDEN);
            return;
        }
        File file = new File(path);
        if (file.isHidden() || !file.exists()) {
            sendError(ctx, NOT_FOUND);
            return;
        }
        if (file.isDirectory()) {
            //判断是否是重定向，为了使当前页路径以/结尾，从而使a链接的相对路径拼接上正确的跟路径
            if (uri.endsWith("/")) {
                sendFileList(ctx, file);
            } else {
                sendRedirect(ctx, uri + "/");
            }
            return;
        }
        //不是目录也不是文件，返回禁止访问
        if (!file.isFile()) {
            sendError(ctx, FORBIDDEN);
            return;
        }
        RandomAccessFile randomAccessFile;
        try {
            randomAccessFile = new RandomAccessFile(file, "r");//以只读方式打开文件
        } catch (FileNotFoundException e) {
            sendError(ctx, NOT_FOUND);
            return;
        }
        long length = randomAccessFile.length();
        HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, OK);
        //指明Content-Length,则即使没有关闭连接,数据也能被返回
        setContentLength(response, length);
        setContentTypeHeader(response, file);
        //如果是长连接则在响应头中设置长连接属性
        if (isKeepAlive(request)) {
            response.headers().set(CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        }
        ctx.write(response);
        ChannelFuture sendFileFuture = ctx.write(
                new ChunkedFile(randomAccessFile, 0,
                        length, 8192), ctx.newProgressivePromise());
        sendFileFuture.addListener(new ChannelProgressiveFutureListener() {
            @Override
            public void operationProgressed(ChannelProgressiveFuture future, long progress, long total) throws Exception {
                if (total < 0)
                    System.err.println("Transfer progress : " + progress);
                else
                    System.err.println("Transfer progress : " + progress + "/" + total);
            }

            //发送完成
            @Override
            public void operationComplete(ChannelProgressiveFuture future) throws Exception {
                System.out.println("Transfer complete");
            }
        });
        ChannelFuture lastContentFuture = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
        //不是长连接则及时关闭管道
        if (!isKeepAlive(request)) {
            lastContentFuture.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (ctx.channel().isActive()) {
            sendError(ctx, INTERNAL_SERVER_ERROR);
        }
    }


    private void setContentTypeHeader(HttpResponse response, File file) {
        MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();
        response.headers().set(CONTENT_TYPE, mimetypesFileTypeMap.getContentType(file));
    }

    private void setContentLength(HttpResponse response, long length) {
        response.headers().set(CONTENT_LENGTH, length);
    }

    private String sanitizeUri(String uri) {
        try {

            uri = URLDecoder.decode(uri, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            try {
                uri = URLDecoder.decode(uri, "ISO8859-1");
            } catch (UnsupportedEncodingException e1) {
                new Error("can not decode the request root");
            }
        }
        if (!uri.startsWith(root)) {
            return null;
        }
        if (!uri.startsWith("/")) {
            return null;
        }
        uri = uri.replace("/", File.separator);
        if (uri.contains(File.separator + ".")
                || uri.contains('.' + File.separator)
                || uri.endsWith(".")
                || INSECURE_URI.matcher(uri).matches())
            return null;
        return System.getProperty("user.dir") + File.separator + uri;
    }

    private static final Pattern INSECURE_URI = Pattern.compile(".*[<>&\"].*");//不合法的路径
    private static final Pattern ALLOWED_FILE_NAME = Pattern.compile("[A-Za-z0-9][-_A-Za-z0-9\\.]*");

    private static void sendFileList(ChannelHandlerContext ctx, File dir) {
        DefaultFullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, OK);
        fullHttpResponse.headers().set(CONTENT_TYPE, "text/html;charset=UTF-8");
        StringBuilder builder = new StringBuilder();
        builder.append("<html lang='en'><head><meta charset='UTF-8'><title>文件服务器</title></head><body><h1>目录 ")
                .append(dir.getAbsolutePath() + "：</h1><br/><ul>")
                .append("<li><a href='/../'>上级目录:</a></li>");//对于a链接来说 ../ 就是当前路径的上一级
        for (File file : dir.listFiles()) {
            if (file.isHidden() || !file.canRead())
                continue;
            if (!ALLOWED_FILE_NAME.matcher(file.getName()).matches())
                continue;
            builder.append("<li>查看: <a href ='");
            //这里根据相对路径来进行请求HTTP请求
            builder.append(file.getName()).append("'> ").append(file.getName()).append("</a></li>");
        }
        builder.append("</ul></body><html>");
        ByteBuf byteBuf = Unpooled.copiedBuffer(builder, CharsetUtil.UTF_8);
        fullHttpResponse.content().writeBytes(byteBuf);
        byteBuf.release();
        ctx.writeAndFlush(fullHttpResponse).addListener(ChannelFutureListener.CLOSE);
    }

    private void sendRedirect(ChannelHandlerContext ctx, String path) {
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, FOUND);
        response.headers().set(LOCATION, path);
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                status, Unpooled.copiedBuffer("Failure : " + status.toString() + "\r\n", CharsetUtil.UTF_8));
        response.headers().set(CONTENT_TYPE, "text/plain;charset=utf-8");
        response.headers().set(ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

}
