package william.mybatis.sql.generator;

import org.apache.ibatis.annotations.Param;

/**
 * @Auther: ZhangShenao
 * @Date: 2018/8/12 13:09
 * @Description:
 */
public abstract class SqlGeneratorAdapter<T extends IEntity> {
    public String insert(@Param("entity") T entity){
        return SimpleSqlGenerator.generateSimpleInsert(entity,getTableName(),getKeyProperty());
    }

    public String update(@Param("entity")T entity){
        return SimpleSqlGenerator.generateSimpleUpdate(entity,getTableName(),getKeyProperty());
    }

    protected abstract String getTableName();

    protected abstract String getKeyProperty();
}
