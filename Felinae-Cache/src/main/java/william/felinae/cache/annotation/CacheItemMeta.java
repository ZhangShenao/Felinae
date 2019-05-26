package william.felinae.cache.annotation;

import william.felinae.cache.constant.CacheType;

/**
 * @Author: ZhangShenao
 * @Date: 2019/5/26 11:34
 * @Description:
 */
public class CacheItemMeta {
    public static final long DEFAULT_MAX_SIZE = 100L;
    public static final long DEFAULT_EXPIRE_TIME_MILLIS = 60L;
    public static final boolean CACHE_NULL_VALUES = true;

    //Required
    private String name;
    private CacheType cacheType;

    //Optional
    private long maxSize = DEFAULT_MAX_SIZE;
    private long expireTimeSeconds = DEFAULT_EXPIRE_TIME_MILLIS;
    private boolean cacheNullValues = CACHE_NULL_VALUES;

    public static Builder newBuilder(String name, CacheType cacheType) {
        return new Builder(name, cacheType);
    }

    private CacheItemMeta() {
    }

    public String getName() {
        return name;
    }

    public CacheType getCacheType() {
        return cacheType;
    }

    public long getMaxSize() {
        return maxSize;
    }

    public long getExpireTimeSeconds() {
        return expireTimeSeconds;
    }

    public boolean isCacheNullValues() {
        return cacheNullValues;
    }

    public static class Builder {
        private CacheItemMeta meta;

        public Builder(String name, CacheType cacheType) {
            meta = new CacheItemMeta();
            meta.name = name;
            meta.cacheType = cacheType;
        }

        public Builder maxSize(long maxSize){
            meta.maxSize = maxSize;
            return this;
        }

        public Builder expireTimeSeconds(long expireTimeSeconds){
            meta.expireTimeSeconds = expireTimeSeconds;
            return this;
        }

        public Builder cacheNullValues(boolean cacheNullValues){
            meta.cacheNullValues = cacheNullValues;
            return this;
        }

        public CacheItemMeta build(){
            return meta;
        }
    }
}
