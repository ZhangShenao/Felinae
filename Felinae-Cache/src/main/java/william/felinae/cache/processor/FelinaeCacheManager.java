package william.felinae.cache.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleCacheManager;

/**
 * @Author: ZhangShenao
 * @Date: 2019/5/26 13:44
 * @Description:
 */
public class FelinaeCacheManager extends SimpleCacheManager {
    @Autowired
    private CacheCreator creator;

    @Override
    protected Cache getMissingCache(String name) {
        return creator.createCache(name).orElseThrow(() -> new RuntimeException("No Cache Specified! name: " + name));
    }
}
