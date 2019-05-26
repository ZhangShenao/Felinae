package william.felinae.cache.annotation;

import william.felinae.cache.constant.CacheType;
import java.lang.annotation.*;
import static william.felinae.cache.constant.CacheType.*;

/**
 * @Author: ZhangShenao
 * @Date: 2019/5/26 11:19
 * @Description:
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CacheItem {
    String[] value();

    long maxSize() default 100L;

    long expireTimeSeconds() default 60L;

    boolean cacheNullValues() default true;

    CacheType cacheType() default LOCAL;

}
