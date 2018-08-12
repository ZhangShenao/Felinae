package william.mybatis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import william.mybatis.processor.MapperBeanPostProcessor;

/**
 * @Auther: ZhangShenao
 * @Date: 2018/8/12 13:47
 * @Description:
 */
@Configuration
public class MyBatisAutoConfiguration {
    @Bean
    public MapperBeanPostProcessor mapperBeanPostProcessor(){
        return new MapperBeanPostProcessor();
    }

}
