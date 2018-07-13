package william.felinae.json.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

/**
 * @Auther: ZhangShenao
 * @Date: 2018/7/12 15:25
 * @Description: 基于Jackson框架的Json序列化/反序列化工具
 */
public class JacksonUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static Logger logger = LoggerFactory.getLogger(JacksonUtil.class);

    public static String writeValueAsString(Object value) {
        Objects.requireNonNull(value);
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            logger.error("JacksonUtil#writeValueAsString: write value error,value class: " + value.getClass(), e);
            throw new RuntimeException(e);
        }
    }

    public static <T> T readValue(String value, Class<T> clazz) {
        Objects.requireNonNull(value);
        try {
            return objectMapper.readValue(value, clazz);
        } catch (IOException e) {
            logger.error("JacksonUtil#readValue: read value error,value class: " + value.getClass(), e);
            throw new RuntimeException(e);
        }
    }

    public static <T> T readValue(URL url, Class<T> clazz) {
        Objects.requireNonNull(url);
        try {
            return objectMapper.readValue(url, clazz);
        } catch (IOException e) {
            logger.error("JacksonUtil#readValue: read value error,value class :" + clazz, e);
            throw new RuntimeException(e);
        }
    }

    public static <T> T readValue(String value, TypeReference valueTypeRef) {
        Objects.requireNonNull(value);
        try {
            return objectMapper.readValue(value, valueTypeRef);
        } catch (IOException e) {
            logger.error("JacksonUtil#readValue: read value error,value class " + valueTypeRef.getType().getTypeName(), e);
            throw new RuntimeException(e);
        }
    }
}
