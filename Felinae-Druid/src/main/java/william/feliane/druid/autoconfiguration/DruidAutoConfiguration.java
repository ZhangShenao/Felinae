package william.feliane.druid.autoconfiguration;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: ZhangShenao
 * @Date: 2018/7/5 13:50
 * @Description: Druid数据源自动配置
 */
@Configuration
@EnableConfigurationProperties(DruidStatProperties.class)
@ConditionalOnClass(DruidDataSource.class)
@ConditionalOnProperty(name = "spring.datasource.type",havingValue = "com.alibaba.druid.pool.DruidDataSource")
public class DruidAutoConfiguration {
    @Autowired
    private DruidStatProperties druidStatProperties;

    //注册Druid数据源,并绑定spring.datasource配置的数据源属性
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    @ConditionalOnMissingBean(DruidDataSource.class)
    public DataSource druidDataSource(){
        return new DruidDataSource();
    }

    //配置Druid后台监控的Servlet
    @Bean
    @ConditionalOnProperty(name = "druid.stat.active")
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(
                    new StatViewServlet(),druidStatProperties.getServletUrlMappings());
        Map<String,String> initParams = new HashMap<>();
        initParams.put("loginUsername",druidStatProperties.getLoginUsername());
        initParams.put("loginPassword",druidStatProperties.getLoginPassword());
        initParams.put("allow",druidStatProperties.getAllow());         //默认就是允许所有访问
//         initParams.put("deny",druidStatProperties.getDeny());
        servletRegistrationBean.setInitParameters(initParams);
        return servletRegistrationBean;
    }

    //配置Druid后台监控的Filter
    @Bean
    @ConditionalOnProperty(name = "druid.stat.active")
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        Map<String,String> initParams = new HashMap<>();
        initParams.put("exclusions",druidStatProperties.getFilterExclusions());
        filterRegistrationBean.setInitParameters(initParams);
        filterRegistrationBean.setUrlPatterns(druidStatProperties.getFilterUrlPatterns());
        return filterRegistrationBean;
    }

}
