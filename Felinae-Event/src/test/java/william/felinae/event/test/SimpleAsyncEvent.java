package william.felinae.event.test;


import william.felinae.event.annotation.AsyncEvent;

/**
 * @Auther: ZhangShenao
 * @Date: 2018/10/15 12:53
 * @Description:简单异步事件
 */
@AsyncEvent
public class SimpleAsyncEvent {
    private String payload;

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
