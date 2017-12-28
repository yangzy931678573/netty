import java.io.File;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by Administrator on 2017/12/28.
 * Description:
 */
public class Tes01 {
    public static void main(String[] args) {
       /* File file = new File("src/test/java/index.html");//相对于整个项目而言
        System.out.println(file.exists());*/
       /* URL resource = Tes01.class.getResource("/index.html");
        String src = resource.getFile();
        File file = new File(src);
        System.out.println(file.exists());*/
        InputStream inputStream = Tes01.class.getResourceAsStream("index.html");
        System.out.println(inputStream);
    }
}
