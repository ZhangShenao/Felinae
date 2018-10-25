package william.felinae.event.test;


import william.felinae.event.annotation.SyncEvent;

/**
 * @Auther: ZhangShenao
 * @Date: 2018/10/15 12:53
 * @Description:简单同步事件
 */
@SyncEvent
public class SimpleSyncEvent {
    private String payload;

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
