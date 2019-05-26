package william.felinae.cache.processor;

import william.felinae.cache.annotation.CacheItem;
import william.felinae.cache.annotation.CacheItemMeta;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: ZhangShenao
 * @Date: 2019/5/26 11:33
 * @Description:
 */
public class CacheItemMetaRegistry {
    private ConcurrentHashMap<String, CacheItemMeta> items = new ConcurrentHashMap<>();

    public void addCacheItem(CacheItem item) {
        Arrays.stream(item.value()).forEach(name -> {
            CacheItemMeta meta = createCacheItemMeta(name, item);
            items.putIfAbsent(name, meta);
        });
    }

    public CacheItemMeta getMeta(String name){
        return items.get(name);
    }

    private CacheItemMeta createCacheItemMeta(String name, CacheItem item) {
        return CacheItemMeta.newBuilder(name, item.cacheType())
                .cacheNullValues(item.cacheNullValues())
                .maxSize(item.maxSize())
                .expireTimeSeconds(item.expireTimeSeconds())
                .build();
    }
}
