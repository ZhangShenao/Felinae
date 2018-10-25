package william.felinae.event.annotation;

import org.springframework.context.annotation.Import;
import william.felinae.event.AsyncEventConfig;
import java.lang.annotation.*;

/**
 * @Auther: ZhangShenao
 * @Date: 2018/10/12 16:06
 * @Description:开启异步事件
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(AsyncEventConfig.class)
@Documented
public @interface EnableAsyncEvent {
}
