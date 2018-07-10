package william.mybatis.utils;

import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.StringUtils;
import william.mybaits.annotation.AutoResult;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.CaseFormat.LOWER_CAMEL;
import static com.google.common.base.CaseFormat.LOWER_UNDERSCORE;

/**
 * @Auther: ZhangShenao
 * @Date: 2018/7/10 15:51
 * @Description: MyBatis Mapper的后处理器,扫描所有Mapper上的@AutoResult注解,并自动注册ResultMap
 */
public class MapperBeanPostProcessor implements BeanPostProcessor{
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof MapperFactoryBean){
            return bean;
        }
        MapperFactoryBean<?> mapperFactoryBean = (MapperFactoryBean<?>)bean;
        Configuration configuration = mapperFactoryBean.getSqlSession().getConfiguration();
        Class<?> mapperInterface = mapperFactoryBean.getMapperInterface();
        String namespace = mapperInterface.getName();

        if (mapperInterface.isAnnotationPresent(AutoResult.class)) {
            AutoResult autoResult = mapperInterface.getAnnotation(AutoResult.class);
            registerResultMap(configuration, namespace, autoResult);
        }

        return bean;
    }

    private void registerResultMap(Configuration configuration, String namespace, AutoResult autoResult) {
        Class<?> entityClass = autoResult.value();
        String resultMapId = autoResult.resultMapId();
        if (StringUtils.isEmpty(resultMapId)) {
            resultMapId = entityClass.getSimpleName();
        }

        Field[] declaredFields = entityClass.getDeclaredFields();
        List<ResultMapping> resultMappings = Arrays.stream(declaredFields)
                .filter(field -> (!Modifier.isStatic(field.getModifiers())))    //忽略静态属性
                .map(field -> {
                    String propertyName = field.getName();
                    String column = LOWER_CAMEL.to(LOWER_UNDERSCORE, propertyName);
                    Class<?> type = field.getType();
                    ResultMapping resultMapping = new ResultMapping.Builder(configuration, propertyName, column, type).build();
                    return resultMapping;
                })
                .collect(Collectors.toList());
        String absoluteId = namespace + "." + resultMapId;
        ResultMap resultMap = new ResultMap.Builder(configuration, absoluteId, entityClass, resultMappings).build();
        configuration.getTypeAliasRegistry().registerAlias(resultMapId, entityClass);
        configuration.addResultMap(resultMap);
    }


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
