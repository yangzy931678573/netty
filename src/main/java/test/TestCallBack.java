package test;

import org.junit.Test;

/**
 * Created by Administrator on 2018/1/19.
 * Description: 回调接口的设计尝试
 */
public class TestCallBack {
    @Test
    public void test() {
        Answer answer = new Answer();
        CallBack echo = new Echo();
        answer.register(echo);
        answer.main();
    }
}


class Echo implements CallBack {
    // 实际的业务方法
    @Override
    public void call(String result) {
        // While the server has a result , this method will invoke ;
        // We can handle our service in this method
        System.out.println(result);
    }
}

class Answer {
    private int status;
    //预设的接口类型
    private CallBack callBack;

    //注册事件
    public void register(CallBack callBack) {
        this.callBack = callBack;
    }

    public void answer(CallBack back) {
        if (this.status == 200)
            this.callBack.call("OK");
        else if (this.status == 300)
            this.callBack.call("Failed");
        else
            this.callBack.call("Wait");
        back.call("This functional interface");
    }

    //这是事件总线,它会在程序执行时改变status,并主动通知回调
    public void main() {
        //change the status ,so we can call the Echo with different data;
        this.status = 200;
        if (this.callBack == null)
            return;
        if (this.status != 0)
            answer((result) -> System.out.println(result));
        else {
            this.status = 400;
            answer((result) -> System.out.println("support functional interface : " + result));
        }
    }
}

interface CallBack {
    void call(String result);
}