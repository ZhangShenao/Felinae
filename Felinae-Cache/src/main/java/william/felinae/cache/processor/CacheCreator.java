package william.felinae.cache.processor;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.util.Assert;
import william.felinae.cache.annotation.CacheItemMeta;
import william.felinae.cache.constant.CacheType;
import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import static william.felinae.cache.constant.CacheType.*;

/**
 * @Auther: ZhangShenao
 * @Date: 2018/9/20 10:39
 * @Description:
 */
public class CacheCreator {
    @Autowired
    private CacheItemMetaRegistry registry;

    @Autowired
    @Qualifier("felinaeCacheManager")
    private SimpleCacheManager cacheManager;

    @Autowired
    @Qualifier("felinaeRedisCacheConfiguration")
    private RedisCacheConfiguration felinaeRedisCacheConfiguration;

    @Autowired(required = false)
    private RedisConnectionFactory redisConnectionFactory;

    public Optional<Cache> createCache(String name) {
        CacheItemMeta meta = registry.getMeta(name);
        return meta == null ? Optional.empty() : Optional.ofNullable(createCache(meta));
    }

    private FelinaeRedisCacheManager felinaeRedisCacheManager;

    @PostConstruct
    private void init() {
        if (redisConnectionFactory != null) {
            RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
            felinaeRedisCacheManager = new FelinaeRedisCacheManager(redisCacheWriter, felinaeRedisCacheConfiguration);
        }
    }

    private Cache createCache(CacheItemMeta meta) {
        CacheType type = meta.getCacheType();
        if (LOCAL == type) {
            return createCaffeineCache(meta);
        }

        if (REDIS == type) {
            Assert.notNull(felinaeRedisCacheManager, "Redis Cache Configured,But No Redis Context Available!! ");
            return createRedisCache(felinaeRedisCacheManager, meta);
        }
        throw new IllegalStateException("Illegal Cache Type: " + type);
    }

    private CaffeineCache createCaffeineCache(CacheItemMeta meta) {
        com.github.benmanes.caffeine.cache.Cache<Object, Object> caffeine = Caffeine.newBuilder()
                .recordStats()
                .expireAfterWrite(meta.getExpireTimeSeconds(), TimeUnit.SECONDS)
                .maximumSize(meta.getMaxSize())
                .build();
        return new CaffeineCache(meta.getName(), caffeine, meta.isCacheNullValues());
    }

    private RedisCache createRedisCache(FelinaeRedisCacheManager cacheManager, CacheItemMeta meta) {
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(meta.getExpireTimeSeconds()))
                .serializeKeysWith(felinaeRedisCacheConfiguration.getKeySerializationPair())
                .serializeValuesWith(felinaeRedisCacheConfiguration.getValueSerializationPair());
        if (!meta.isCacheNullValues()) {
            configuration.disableCachingNullValues();
        }
        return cacheManager.createRedisCache(meta.getName(), configuration);
    }
}
