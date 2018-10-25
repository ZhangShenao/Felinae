package william.felinae.event;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import william.felinae.event.config.AsyncEventExecutorProperty;
import william.felinae.event.processor.AsyncEventFactoryPostProcessor;

/**
 * @Auther: ZhangShenao
 * @Date: 2018/10/12 16:08
 * @Description:异步事件配置类
 */
@Configuration
@EnableConfigurationProperties(AsyncEventExecutorProperty.class)
public class AsyncEventConfig {
    @Bean
    public ThreadPoolTaskExecutor ginEventTaskExecutor(AsyncEventExecutorProperty property){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(property.getCorePoolSize());
        executor.setMaxPoolSize(property.getMaxPoolSize());
        executor.setKeepAliveSeconds(property.getKeepAliveSeconds());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }

    @Bean
    public static AsyncEventFactoryPostProcessor felinaeAsyncEventFactoryPostProcessor(@Qualifier("ginEventTaskExecutor") ThreadPoolTaskExecutor executor){
        return new AsyncEventFactoryPostProcessor(executor);
    }
}
