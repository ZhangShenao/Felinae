package william.felinae.rest.autoconfiguration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import william.felinae.rest.client.RestClient;
import java.nio.charset.StandardCharsets;

/**
 * @Auther: ZhangShenao
 * @Date: 2018/7/12 15:38
 * @Description: Rest自动配置类
 */
@Configuration
@ConditionalOnClass(RestTemplate.class)
@Import(RestClient.class)
public class RestAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public RestTemplate encodedRestTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));     //解决中文乱码问题
        return restTemplate;
    }



}
