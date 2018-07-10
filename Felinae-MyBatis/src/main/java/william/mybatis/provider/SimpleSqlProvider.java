package william.mybatis.provider;

import org.apache.ibatis.annotations.Param;
import william.mybatis.utils.IEntity;
import william.mybatis.utils.SimpleSqlGenerator;

/**
 * @Auther: ZhangShenao
 * @Date: 2018/7/10 15:33
 * @Description: 简单SQL语句Provider,子类可继承该类生成SQL语句
 */
public abstract class SimpleSqlProvider<T extends IEntity> {
    public String insert(@Param("entity") T entity){
        return SimpleSqlGenerator.generateSimpleInsert(entity,getTableName(),getKeyPropName());
    }

    public String update(@Param("entity") T entity){
        return SimpleSqlGenerator.generateSimpleUpdate(entity,getTableName(),getKeyPropName());
    }

    protected abstract String getTableName();

    protected abstract String getKeyPropName();
}
