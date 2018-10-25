package william.felinae.cache;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.CollectionUtils;
import william.felinae.cache.config.CacheProperty;
import william.felinae.cache.config.FelinaeGinRedisCacheManager;
import william.felinae.cache.util.CacheCreator;
import java.util.LinkedList;
import java.util.List;

/**
 * @Auther: ZhangShenao
 * @Date: 2018/9/2 11:42
 * @Description:
 */
@Configuration
@ComponentScan
public class FelinaeCacheAutoConfiguration {
    @Autowired
    private CacheCreator cacheCreator;

    private static final Logger logger = LoggerFactory.getLogger(FelinaeCacheAutoConfiguration.class);

    @Bean
    public RedisCacheConfiguration felinaeRedisCacheConfiguration(){
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
    public FelinaeGinRedisCacheManager felinaeGinRedisCacheManager(RedisConnectionFactory redisConnectionFactory,
                                                            @Qualifier("felinaeRedisCacheConfiguration") RedisCacheConfiguration redisCacheConfiguration){
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
        FelinaeGinRedisCacheManager felinaeGinRedisCacheManager = new FelinaeGinRedisCacheManager(redisCacheWriter, redisCacheConfiguration);
        return felinaeGinRedisCacheManager;
    }

    @Bean
    @Primary
    public SimpleCacheManager felinaeCacheManager(@Qualifier("cacheProperties") List<CacheProperty> cacheProperties) {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        if (CollectionUtils.isEmpty(cacheProperties)){
            logger.info("No Cache Configured");
            return cacheManager;
        }

        List<Cache> caches = new LinkedList<>();

        for (CacheProperty cacheProperty : cacheProperties){
            Cache cache = cacheCreator.createGinCache(cacheProperty);
            caches.add(cache);
        }
        cacheManager.setCaches(caches);
        return cacheManager;
    }

}
