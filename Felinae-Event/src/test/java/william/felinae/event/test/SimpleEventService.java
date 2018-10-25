package william.felinae.event.test;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * @Auther: ZhangShenao
 * @Date: 2018/10/15 12:54
 * @Description: 事件处理器类
 */
@Service
public class SimpleEventService {
    @EventListener(SimpleAsyncEvent.class)   //注册事件监听器
    public void onSimpleAsyncEvent(SimpleAsyncEvent simpleAsyncEvent){
        System.err.println("On SimpleAsyncEvent,payload: " + simpleAsyncEvent.getPayload());
    }

    @EventListener(SimpleSyncEvent.class)
    public void onSimpleSyncEvent(SimpleSyncEvent simpleSyncEvent){
        System.err.println("On SimpleSyncEvent,payload: " + simpleSyncEvent.getPayload());
    }
}
