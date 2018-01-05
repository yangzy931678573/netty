package netty.proprietaryProtocol.message;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/3.
 * Description:   Netty协议栈消息头 , 序列化时按照字段的顺序
 */
public class Header {
    /**
     * 校验码:由0xabef + 主版本号 + 次版本号 4个字节
     */
    private int crcCode = 0xabef0101;
    /**
     * 消息长度:包括消息头和消息体 4个字节
     */
    private int length;
    /**
     * 会话ID:集群节点内唯一,由会话ID生成器生成  8个字节
     */
    private long sessionId;
    /**
     * 消息类型:0业务请求消息 1业务响应消息 2业务ONE WAY消息(既是请求 又是响应) 3握手请求消息
     *        4握手应答消息 5心跳请求消息 6心跳应答消息 1个字节
     */
    private byte type;
    /**
     * 消息优先级:0~255 1个字节
     */
    private byte priority;
    /**
     * 附件，可选字段，用于扩展消息
     */
    private Map<String, Object> attachment = new HashMap<>(0);

    public int getCrcCode() {
        return crcCode;
    }

    public void setCrcCode(int crcCode) {
        this.crcCode = crcCode;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public long getSessionId() {
        return sessionId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte getPriority() {
        return priority;
    }

    public void setPriority(byte priority) {
        this.priority = priority;
    }

    public Map<String, Object> getAttachment() {
        return attachment;
    }

    public void setAttachment(Map<String, Object> attachment) {
        this.attachment = attachment;
    }

    @Override
    public String toString() {
        return "Header{" +
                "crcCode=" + crcCode +
                ", length=" + length +
                ", sessionId=" + sessionId +
                ", type=" + type +
                ", priority=" + priority +
                ", attachment=" + attachment +
                '}';
    }
}
