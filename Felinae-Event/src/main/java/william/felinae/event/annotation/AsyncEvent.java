package william.felinae.event.annotation;

import java.lang.annotation.*;

/**
 * @Auther: ZhangShenao
 * @Date: 2018/10/16 13:16
 * @Description:标记异步事件类
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AsyncEvent {
}
