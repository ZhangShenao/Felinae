package william.felinae.jms.processor;

import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import william.felinae.jms.property.FelinaeActiveMQConnectionProperty;
import william.felinae.json.util.JacksonUtil;
import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.net.URL;

/**
 * @Auther: ZhangShenao
 * @Date: 2018/11/8 13:47
 * @Description:
 */
@Component
public class FelinaeActiveMQPropertyResolver {
    public interface DefaultActiveMQProperties {
        String DEFAULT_BROKER_URL = "tcp://127.0.0.1:61616";
        String ADMIN_USERNAME = "admin";
        String ADMIN_PASSWORD = "admin";
        long RECOVERY_INTERVAL_MILLIS = 1000L;
    }

    private FelinaeActiveMQConnectionProperty property;

    private static final String PROPERTY_FILE_NAME = "classpath:jms.json";

    private static final Logger logger = LoggerFactory.getLogger(FelinaeActiveMQPropertyResolver.class);


    public FelinaeActiveMQConnectionProperty getProperty() {
        return property;
    }

    @PostConstruct
    private void resolveAcviveMQProperty(){
        String propertyFileName = PROPERTY_FILE_NAME;
        try {
            URL url = ResourceUtils.getURL(propertyFileName);
            if (url == null){
                wrapWithDefaultValue();
                return;
            }
            property = JacksonUtil.readValue(url, new TypeReference<FelinaeActiveMQConnectionProperty>() {});
        } catch (FileNotFoundException e) {
            wrapWithDefaultValue();
        } catch (Exception e) {
            throw new IllegalStateException("Resolve ActiveMQ Property Error,Please Check File: " + propertyFileName,e);
        }

    }

    private void wrapWithDefaultValue(){
        if (logger.isWarnEnabled()){
            logger.warn("No Available Configuration for ActiveMQ,Use Default Configuration");
        }
        property = new FelinaeActiveMQConnectionProperty();
        property.setBrokerURL(DefaultActiveMQProperties.DEFAULT_BROKER_URL);
        property.setAdminUsername(DefaultActiveMQProperties.ADMIN_USERNAME);
        property.setAdminPassword(DefaultActiveMQProperties.ADMIN_PASSWORD);
        property.setRecoveryIntervalMillis(DefaultActiveMQProperties.RECOVERY_INTERVAL_MILLIS);
    }

}
