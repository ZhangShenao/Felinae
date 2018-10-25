package william.felinae.event.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.support.AbstractApplicationContext;

import java.util.concurrent.Executor;

/**
 * @Auther: ZhangShenao
 * @Date: 2018/10/16 12:43
 * @Description:异步事件工厂后处理器,向容器中注册AsyncApplicationEventMulticaster
 */
public class AsyncEventFactoryPostProcessor implements BeanFactoryPostProcessor {
    private Executor executor;

    public AsyncEventFactoryPostProcessor(Executor executor) {
        this.executor = executor;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        if (beanFactory.containsLocalBean(AbstractApplicationContext.APPLICATION_EVENT_MULTICASTER_BEAN_NAME)) {
            ApplicationEventMulticaster multicaster = beanFactory.getBean(AbstractApplicationContext.APPLICATION_EVENT_MULTICASTER_BEAN_NAME, ApplicationEventMulticaster.class);
            if (multicaster instanceof FelinaeAsyncApplicationEventMulticaster){
                return;
            }
        }
        else {
            FelinaeAsyncApplicationEventMulticaster felinaeAsyncApplicationEventMulticaster = new FelinaeAsyncApplicationEventMulticaster();
            felinaeAsyncApplicationEventMulticaster.setTaskExecutor(executor);
            felinaeAsyncApplicationEventMulticaster.setBeanFactory(beanFactory);
            beanFactory.registerSingleton(AbstractApplicationContext.APPLICATION_EVENT_MULTICASTER_BEAN_NAME, felinaeAsyncApplicationEventMulticaster);
        }
    }
}
