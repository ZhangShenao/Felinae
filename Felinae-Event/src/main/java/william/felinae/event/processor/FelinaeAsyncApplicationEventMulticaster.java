package william.felinae.event.processor;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.AnnotationUtils;
import william.felinae.event.annotation.AsyncEvent;
import java.util.concurrent.Executor;

/**
 * @Auther: ZhangShenao
 * @Date: 2018/10/16 11:46
 * @Description:异步事件分发器
 */
public class FelinaeAsyncApplicationEventMulticaster extends SimpleApplicationEventMulticaster {

    @Override
    public void multicastEvent(ApplicationEvent event, ResolvableType eventType) {
        ResolvableType type = (eventType != null ? eventType : resolveDefaultEventType(event));
        for (final ApplicationListener<?> listener : getApplicationListeners(event, type)) {
            Executor executor = getTaskExecutor();
            if (executor != null && needAsyncExec(event)) {
                executor.execute(() -> invokeListener(listener, event));
            }
            else {
                invokeListener(listener, event);
            }
        }
    }

    private ResolvableType resolveDefaultEventType(ApplicationEvent event) {
        return ResolvableType.forInstance(event);
    }

    private boolean needAsyncExec(ApplicationEvent event){
        if (!(event instanceof PayloadApplicationEvent)){
            return false;
        }
        PayloadApplicationEvent payloadApplicationEvent = (PayloadApplicationEvent)event;
        Object payload = payloadApplicationEvent.getPayload();
        AsyncEvent asyncEvent = AnnotationUtils.findAnnotation(payload.getClass(), AsyncEvent.class);
        return (asyncEvent != null);
    }
}
