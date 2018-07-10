package william.mybaits.annotation;

import java.lang.annotation.*;

/**
 * @Auther: ZhangShenao
 * @Date: 2018/7/10 15:46
 * @Description: MyBaits的自动结果映射器,用于MyBatis的Mapper上,将MyBatis查询结果映射成Enyity,要求数据库列名为下划线形式,Entity属性名为camel形式
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AutoResult {
    Class<?> value();   //指定自动映射的Entity Class

    String resultMapId() default "";    //指定映射的ResultMap的id,默认为Entity的简单类名
}
