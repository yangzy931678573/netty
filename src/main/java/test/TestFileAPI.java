package test;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2017/12/28.
 * Description: 测试File的API的使用
 */
public class TestFileAPI {
    public static void main(String[] args) {
        File file = new File("src/main/java/");
        System.out.println(file.exists() + ":" + file.getPath());
        file = new File(System.getProperty("user.dir") + File.separator + "/src/main/java");
        System.out.println(file.exists());
        System.out.println(file.getPath());//getPath()方法获取的是初始化File实例时指定的路径，可以是绝对路径也可以是相对路径
        System.out.println(file.getAbsolutePath());
        System.out.println("/src/main/java/".replace("/", File.separator));
        if (file.exists()) {
            if (file.isDirectory()) {
                for (File f : file.listFiles()) {
                    System.out.println("absolute path : " + f.getAbsolutePath());
                    System.out.println("file name : " + f.getName());
                    System.out.println("file path : " + f.getPath());
                }
            } else {
                System.out.println("absolute path : " + file.getAbsolutePath());
                System.out.println("file name : " + file.getName());
                System.out.println("file path : " + file.getPath());
            }
        } else {
            System.out.println("file not found");
        }

    }
}
