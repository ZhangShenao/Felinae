package william.felinae.cache.processor;

import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;
import william.felinae.cache.annotation.CacheItem;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: ZhangShenao
 * @Date: 2019/5/26 11:26
 * @Description:@CacheItem注解的后处理器,负责解析@CacheItem注解并注册缓存
 */
public class CacheItemAnnotationBeanPostProcessor implements BeanPostProcessor {
    @Autowired
    private CacheItemMetaRegistry registry;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> targetClass = AopProxyUtils.ultimateTargetClass(bean);
        Set<Method> methods = MethodIntrospector.selectMethods(targetClass, (ReflectionUtils.MethodFilter) method -> method.getAnnotation(CacheItem.class) != null);

        if (CollectionUtils.isEmpty(methods)) {
            return bean;
        }

        methods.stream()
                .map(m -> AnnotatedElementUtils.findAllMergedAnnotations(m, CacheItem.class))
                .flatMap(Collection::stream)
                .collect(Collectors.toList())
                .forEach(c -> registry.addCacheItem(c));
        return bean;
    }
}
