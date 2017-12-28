package netty.msgpack;

import org.msgpack.annotation.Message;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/27.
 * Description: 用户信息实体类
 */
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userName;
    private int age;

    public UserInfo() {

    }

    public UserInfo(String userName, int age) {
        this.userName = userName;
        this.age = age;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userName='" + userName + '\'' +
                ", age=" + age +
                '}';
    }
}
