package william.felinae.event.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import william.felinae.event.AsyncEventConfig;
import william.felinae.event.annotation.EnableAsyncEvent;

/**
 * @Auther: ZhangShenao
 * @Date: 2018/10/25 16:16
 * @Description:
 */
@SpringBootTest(classes = AsyncEventConfig.class)
@EnableAsyncEvent
@ComponentScan
@RunWith(SpringRunner.class)
public class TestEvent {
    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testEvent(){
        //发布事件
        SimpleAsyncEvent simpleAsyncEvent = new SimpleAsyncEvent();
        simpleAsyncEvent.setPayload("payload");
        applicationContext.publishEvent(simpleAsyncEvent);

        SimpleSyncEvent simpleSyncEvent = new SimpleSyncEvent();
        simpleSyncEvent.setPayload("payload");
        applicationContext.publishEvent(simpleSyncEvent);
    }
}
