package william.felinae.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.core.annotation.Order;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import william.felinae.jms.constant.FelinaeActiveMQConstant;
import william.felinae.jms.processor.FelinaeActiveMQPropertyResolver;
import william.felinae.jms.property.FelinaeActiveMQConnectionProperty;
import javax.jms.ConnectionFactory;

/**
 * @Auther: ZhangShenao
 * @Date: 2018/11/8 13:28
 * @Description:
 */
@Configuration
@ComponentScan
public class FelinaeJmsAutoConfiguration {
    @Autowired
    private FelinaeActiveMQPropertyResolver jmsPropertyResolver;

    @ConditionalOnClass(ActiveMQConnectionFactory.class)
    @Bean
    public ConnectionFactory activeMQConnectionFactory(){
        FelinaeActiveMQConnectionProperty activeMQConnectionProperty = jmsPropertyResolver.getProperty();
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(activeMQConnectionProperty.getBrokerURL());
        connectionFactory.setUserName(activeMQConnectionProperty.getAdminUsername());
        connectionFactory.setPassword(activeMQConnectionProperty.getAdminPassword());

        return connectionFactory;
    }

    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

    @ConditionalOnClass(ActiveMQConnectionFactory.class)
    @Bean(name = FelinaeActiveMQConstant.DEFAULT_LISTENER_CONTAINER_FACTORY_NAME)
    public JmsListenerContainerFactory<?> defaultActiveMQListenerContainerFactory(@Qualifier("activeMQConnectionFactory") ConnectionFactory connectionFactory,
                                                                           DefaultJmsListenerContainerFactoryConfigurer configurer,
                                                                           MessageConverter converter) {
        FelinaeActiveMQConnectionProperty activeMQConnectionProperty = jmsPropertyResolver.getProperty();
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setRecoveryInterval(activeMQConnectionProperty.getRecoveryIntervalMillis());
        factory.setMessageConverter(converter);
        factory.setPubSubDomain(true);
        configurer.configure(factory, connectionFactory);
        return factory;
    }
}
