package william.felinae.cache.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @Auther: ZhangShenao
 * @Date: 2018/9/2 11:43
 * @Description:
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CacheProperty {
    public static final String CACHE_TYPE_LOCAL = "local";
    public static final String CACHE_TYPE_REDIS = "redis";

    private static final int DEFAULT_MAX_SIZE = 100;
    private static final int DEFAULT_EXPIRE_SECONDS = 5;
    private static final boolean DEFAULT_CACHE_NULL_VALUES = true;
    private static final String DEFAULT_CACHE_TYPE = CACHE_TYPE_LOCAL;

    private String name;
    private int maxSize = DEFAULT_MAX_SIZE;
    private int expireSeconds = DEFAULT_EXPIRE_SECONDS;
    private boolean cacheNullValues = DEFAULT_CACHE_NULL_VALUES;
    private String type = DEFAULT_CACHE_TYPE;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public int getExpireSeconds() {
        return expireSeconds;
    }

    public void setExpireSeconds(int expireSeconds) {
        this.expireSeconds = expireSeconds;
    }

    public boolean isCacheNullValues() {
        return cacheNullValues;
    }

    public void setCacheNullValues(boolean cacheNullValues) {
        this.cacheNullValues = cacheNullValues;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isLocalCache(){
        return CACHE_TYPE_LOCAL.equalsIgnoreCase(getType());
    }

    public boolean isRedisCache(){
        return CACHE_TYPE_REDIS.equalsIgnoreCase(getType());
    }
}
