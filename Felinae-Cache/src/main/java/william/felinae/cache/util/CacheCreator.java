package william.felinae.cache.util;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.stereotype.Service;
import william.felinae.cache.config.CacheProperty;
import william.felinae.cache.config.FelinaeGinRedisCacheManager;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: ZhangShenao
 * @Date: 2018/9/20 10:39
 * @Description:
 */
@Service
public class CacheCreator {
    @Autowired(required = false)
    private FelinaeGinRedisCacheManager felinaeGinRedisCacheManager;

    @Autowired
    @Qualifier("felinaeRedisCacheConfiguration")
    private RedisCacheConfiguration felinaeRedisCacheConfiguration;

    public Cache createGinCache(CacheProperty cacheProperty){
        if (cacheProperty.isLocalCache()){
            return createCaffeineCache(cacheProperty);
        }
        if (cacheProperty.isRedisCache()){
            if (cacheProperty == null){
                throw new IllegalStateException("Redis Cache Configured,But No Redis Context Available!! Please Check File 'cache.json'!!");
            }
            return createRedisCache(felinaeGinRedisCacheManager,cacheProperty);
        }
        throw new IllegalStateException("Cache Config Error in File 'cache.json'!! cacheKey: " + cacheProperty.getName());
    }

    private CaffeineCache createCaffeineCache(CacheProperty cacheProperty){
        com.github.benmanes.caffeine.cache.Cache<Object, Object> caffeine = Caffeine.newBuilder()
                .recordStats()
                .expireAfterWrite(cacheProperty.getExpireSeconds(), TimeUnit.SECONDS)
                .maximumSize(cacheProperty.getMaxSize())
                .build();
        return new CaffeineCache(cacheProperty.getName(),caffeine, cacheProperty.isCacheNullValues());
    }

    private RedisCache createRedisCache(FelinaeGinRedisCacheManager felinaeGinRedisCacheManager, CacheProperty cacheProperty){
        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(cacheProperty.getExpireSeconds()))
                .serializeKeysWith(felinaeRedisCacheConfiguration.getKeySerializationPair())
                .serializeValuesWith(felinaeRedisCacheConfiguration.getValueSerializationPair());
        if (!cacheProperty.isCacheNullValues()){
            cacheConfiguration.disableCachingNullValues();
        }
        return felinaeGinRedisCacheManager.createRedisCache(cacheProperty.getName(), cacheConfiguration);
    }
}
