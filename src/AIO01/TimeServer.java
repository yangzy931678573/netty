package AIO01;


/**
 * Created by Administrator on 2017/10/17.
 * Description: AIO服务器
 */
public class TimeServer {
    public static void main(String[] args) {
        int port = 8080;
        //在程序启动时，也就是 在 cmd 窗口输入java TimeServer 时，
        //可以在后边跟上一个或多个参数 ，这些参数就是String[] args
        //这也就是为什么可以设置虚拟机启动参数的原因
        if (args!=null && args.length >0){
            try {
                port = Integer.valueOf(args[0]);
            }catch (NumberFormatException e){
                e.printStackTrace();
            }

        }
        AsyncTimeServerHandler timeServer = new AsyncTimeServerHandler(port);
        new Thread(timeServer,"AIO-AsyncTimeServerHandler-001").start();
    }
}
