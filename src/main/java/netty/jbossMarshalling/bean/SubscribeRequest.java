package netty.jbossMarshalling.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/27.
 * Description: 订阅消息请求实体类
 */
public class SubscribeRequest implements Serializable{

    private static  final long serialVersionUID = 1L;

    private int requestId;
    private String userName;
    private String productName;
    private String phoneNumber;
    private String address;

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "SubscribeRequest{" +
                "requestId=" + requestId +
                ", userName='" + userName + '\'' +
                ", productName='" + productName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
