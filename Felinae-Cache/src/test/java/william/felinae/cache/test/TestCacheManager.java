package william.felinae.cache.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringRunner;
import william.felinae.cache.FelinaeCacheAutoConfiguration;

/**
 * @Auther: ZhangShenao
 * @Date: 2018/10/25 14:54
 * @Description:
 */
@SpringBootTest(classes = FelinaeCacheAutoConfiguration.class)
@RunWith(SpringRunner.class)
public class TestCacheManager {
    @Autowired
    private CacheManager cacheManager;

    @Test
    public void testShowCacheManager(){
        System.err.println(cacheManager);
    }
}
