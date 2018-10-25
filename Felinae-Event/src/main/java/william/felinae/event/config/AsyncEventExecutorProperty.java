package william.felinae.event.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Auther: ZhangShenao
 * @Date: 2018/10/12 16:53
 * @Description:异步事件线程池配置
 */
@ConfigurationProperties(prefix = "felinae.event")
public class AsyncEventExecutorProperty {
    private int corePoolSize = 4;
    private int maxPoolSize = 10;
    private int keepAliveSeconds = 30;

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public int getKeepAliveSeconds() {
        return keepAliveSeconds;
    }

    public void setKeepAliveSeconds(int keepAliveSeconds) {
        this.keepAliveSeconds = keepAliveSeconds;
    }
}
