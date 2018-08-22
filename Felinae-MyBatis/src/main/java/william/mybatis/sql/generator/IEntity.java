package william.mybatis.sql.generator;

/**
 * @Auther: ZhangShenao
 * @Date: 2018/8/12 13:11
 * @Description:Entity的标志性接口
 */
public interface IEntity {
    String tableName();

    String keyProperty();
}
