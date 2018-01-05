package netty.fileTransfer;

import java.io.File;
import java.net.URL;

/**
 * Created by Administrator on 2018/1/3.
 * Description: NIO原生 FileChannel API
 */
public class NIOFileChannel {
    public static void main(String[] args)  {
        URL url = Thread.currentThread().getContextClassLoader().getResource("1.txt");
        File file = new File(url.getPath());
        System.out.println(file.length());
      /*  try {
            *//*File f = new File("src/main/resources/1.txt");
            System.out.println(f.getAbsolutePath());
            System.out.println(f.length());
            RandomAccessFile file = new RandomAccessFile(f,"rw");
            FileChannel channel = file.getChannel();*//*

         *//*   String content = "11111111111111111111111111111111";
            ByteBuffer writeBuffer = ByteBuffer.allocate(128);
            writeBuffer.put(content.getBytes());
            writeBuffer.flip();

            channel.write(writeBuffer);

            channel.close();*//*
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
