package william.felinae.cache.config;

import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import william.felinae.json.util.JacksonUtil;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;

/**
 * @Auther: ZhangShenao
 * @Date: 2018/9/2 12:19
 * @Description:
 */
@Service
public class CachePropertyLoader {
    private static final Logger logger = LoggerFactory.getLogger(CachePropertyLoader.class);

    @Bean
    public List<CacheProperty> cacheProperties(){
        try {
            URL url = ResourceUtils.getURL("classpath:cache.json");
            if (url == null){
                return null;
            }
            return JacksonUtil.readValue(url, new TypeReference<List<CacheProperty>>() {});
        } catch (FileNotFoundException e) {
            logger.info("No Cache Config Available");
            return null;
        } catch (Exception e){
            logger.error("Load Cache Config Error!!",e);
            return null;
        }
    }

}
