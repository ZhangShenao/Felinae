package william.felinae.cache;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import william.felinae.cache.processor.FelinaeRedisCacheManager;
import william.felinae.cache.processor.CacheCreator;
import william.felinae.cache.processor.CacheItemAnnotationBeanPostProcessor;
import william.felinae.cache.processor.CacheItemMetaRegistry;
import william.felinae.cache.processor.FelinaeCacheManager;


/**
 * @Auther: ZhangShenao
 * @Date: 2018/9/2 11:42
 * @Description:
 */
@Configuration
public class FelinaeCacheAutoConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(FelinaeCacheAutoConfiguration.class);

    @Bean
    public CacheItemMetaRegistry cacheItemMetaRegistry() {
        return new CacheItemMetaRegistry();
    }

    @Bean
    public CacheItemAnnotationBeanPostProcessor cacheItemAnnotationBeanPostProcessor() {
        return new CacheItemAnnotationBeanPostProcessor();
    }

    @Bean
    public CacheCreator cacheCreator() {
        return new CacheCreator();
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisCacheConfiguration felinaeRedisCacheConfiguration() {
        RedisSerializationContext.SerializationPair<String> keySerializationPair = RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer());

        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        RedisSerializationContext.SerializationPair valueSerializationPair = RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer);

        return RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(keySerializationPair)
                .serializeValuesWith(valueSerializationPair);
    }


    @Bean
    @ConditionalOnBean(RedisConnectionFactory.class)
    public FelinaeRedisCacheManager felinaeGinRedisCacheManager(RedisConnectionFactory redisConnectionFactory,
                                                                @Qualifier("felinaeRedisCacheConfiguration") RedisCacheConfiguration redisCacheConfiguration) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
        FelinaeRedisCacheManager felinaeRedisCacheManager = new FelinaeRedisCacheManager(redisCacheWriter, redisCacheConfiguration);
        return felinaeRedisCacheManager;
    }

    @Bean
    @Primary
    public CacheManager felinaeCacheManager() {
        return new FelinaeCacheManager();
    }

}
