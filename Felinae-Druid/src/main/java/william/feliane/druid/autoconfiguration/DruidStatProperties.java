package william.feliane.druid.autoconfiguration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.Arrays;
import java.util.List;

/**
 * @Auther: ZhangShenao
 * @Date: 2018/7/5 14:33
 * @Description:Druid数据源监控属性
 */
@ConfigurationProperties(prefix = "druid.stat")
public class DruidStatProperties {
    private String loginUsername = "admin";
    private String loginPassword = "123456";
    private String allow = "";
    private String deny = "";
    private String servletUrlMappings = "/druid/*";
    private List<String> filterUrlPatterns = Arrays.asList("/*");
    private String filterExclusions = "*.js,*.css,/druid/*";

    public String getLoginUsername() {
        return loginUsername;
    }

    public void setLoginUsername(String loginUsername) {
        this.loginUsername = loginUsername;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String getAllow() {
        return allow;
    }

    public void setAllow(String allow) {
        this.allow = allow;
    }

    public String getDeny() {
        return deny;
    }

    public void setDeny(String deny) {
        this.deny = deny;
    }

    public String getServletUrlMappings() {
        return servletUrlMappings;
    }

    public void setServletUrlMappings(String servletUrlMappings) {
        this.servletUrlMappings = servletUrlMappings;
    }

    public List<String> getFilterUrlPatterns() {
        return filterUrlPatterns;
    }

    public void setFilterUrlPatterns(List<String> filterUrlPatterns) {
        this.filterUrlPatterns = filterUrlPatterns;
    }

    public String getFilterExclusions() {
        return filterExclusions;
    }

    public void setFilterExclusions(String filterExclusions) {
        this.filterExclusions = filterExclusions;
    }
}
