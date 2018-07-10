package william.mybatis.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.jdbc.SQL;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Auther: ZhangShenao
 * @Date: 2018/7/10 15:16
 * @Description: 简单的SQL语句生成器,结合MyBatis的@xxProvider注解使用,生成通用的CRUD的SQL语句
 * 要求Entity类的属性名为camel风格,对应数据库列名为下划线风格
 */
public class SimpleSqlGenerator {
    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 生成nsert语句
     * @param entity 实体类对象
     * @param tableName 数据库表名
     * @param keyProp 主键列名
     */
    public static <T> String generateSimpleInsert(T entity, String tableName, String keyProp) {
        Map<String, Object> map = toPlainMap(entity);
        return new SQL() {{
            INSERT_INTO(tableName);
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if(!entry.getKey().equals(keyProp) && entry.getValue() != null) {
                    String col = property2Column(entry.getKey());
                    String val = "#{row." + entry.getKey() + "}";
                    VALUES(col, val);
                }
            }
        }}.toString();
    }

    /**
     * 生成Update语句
     * @param entity 实体类对象
     * @param tableName 数据库表名
     * @param keyProp 主键列名
     */
    public static <T> String generateSimpleUpdate(T entity, String tableName, String keyProp) {
        Map<String, Object> map = toPlainMap(entity);
        if (map.values().stream().filter(Objects::nonNull).collect(Collectors.toList()).size() == 0) {
            return "";
        }
        return new SQL() {{
            UPDATE(tableName);
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if(!entry.getKey().equals(keyProp) && entry.getValue() != null) {
                    String col = entry.getKey().replaceAll("([A-Z])", "_$1").toLowerCase().replaceAll("^_", "");
                    String val = "#{row." + entry.getKey() + "}";
                    SET(col + " = " + val);
                }
            }
            WHERE(property2Column(keyProp) + " = #{row." + keyProp + "}");
        }}.toString();
    }

    private static Map<String, Object> toPlainMap(Object entity) {
        try {
            String json = objectMapper.writeValueAsString(entity);
            return objectMapper.readValue(json, new TypeReference<Map<String, Object>>(){});
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private static String property2Column(String property) {
        return property.replaceAll("([A-Z])", "_$1").toLowerCase().replaceAll("^_", "");
    }
}
