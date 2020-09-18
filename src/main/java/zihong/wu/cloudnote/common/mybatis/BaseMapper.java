package zihong.wu.cloudnote.common.mybatis;

import org.apache.ibatis.annotations.*;
import org.springframework.dao.DuplicateKeyException;
import zihong.wu.cloudnote.common.mybatis.provider.*;

import java.util.List;

public interface BaseMapper<T extends BaseEntity> {
    /**
     * 创建表
     * @param entity
     */
    @UpdateProvider(type = BaseCreateProvider.class , method = "create")
    void baseCreate(T entity);


    /**
     * 插入操作
     * 将实体类的所有字段和字段的值分别列出来，适用于主键不是自增的表
     * @param entity
     * @return INSERT INTO tableName (id,name...) VALUES (#{id},#{name}...)
     * @throws DuplicateKeyException 当唯一字段重复插入时，会抛该异常
     */
    @InsertProvider(type = BaseInsertProvider.class,method = "insert")
    Integer baseInsert(T entity) throws DuplicateKeyException;

    /**
     * 插入数据并返回自增的主键(建议使用id)
     * 将实体类中除主键以外的字段和值分别列出来，适用于主键是自增的表
     * @param entity
     * @return INSERT INTO tableName (name...) VALUES(#{name}...)
     * @throws DuplicateKeyException 当唯一字段重复插入时，会抛该异常
     */
    @InsertProvider(type = BaseInsertProvider.class,method = "insertAndReturnKey")
    @Options(useGeneratedKeys=true,keyProperty = "id", keyColumn = "id")
    Integer baseInsertAndReturnKey(T entity) throws DuplicateKeyException;


    /**
     * 根据Id删除数据，要求必须有id字段
     * @param entity
     * @return
     */
    @DeleteProvider(type = BaseDeleteProvider.class,method = "deleteById")
    Integer baseDeleteById(T entity);

    /**
     * 根据条件删除
     * 传入的对象中带@IndexAttribute注解的字段有值的都作为查询条件
     * 多个查询条件用And连接
     * @param entity 实体对象
     * @return DELETE FROM router  WHERE name = #{name} AND serviceName = #{serviceName}
     */
    @SelectProvider(type= BaseDeleteProvider.class,method = "deleteByCondition")
    Integer baseDeleteByCondition(T entity);

    /**
     * 根据id 更新数据，空值不更新 ，要求必须有id字段
     * @param entity
     * @return
     */
    @UpdateProvider(type = BaseUpdateProvider.class,method = "updateById")
    Integer baseUpdateById(T entity);

    /**
     * 根据主键更新数据，空值不更新，要求数据至少有一个主键，且主键有值
     * @param entity
     * @return
     */
    @UpdateProvider(type = BaseUpdateProvider.class,method = "updateByKey")
    Integer baseUpdateByKey(T entity);

    /**
     * 根据Id 查找数据，要求必须有id 字段
     * @param entity
     * @return
     */
    @SelectProvider(type= BaseSelectProvider.class,method = "selectById")
    T baseSelectById(T entity);

    /**
     * 根据主键查询数据，要求至少有一个主键，且主键必须有值
     * @param entity
     * @return
     */
    @SelectProvider(type= BaseSelectProvider.class,method = "selectByKey")
    T baseSelectByKey(T entity);



    /**
     * 查询全部数据
     * @param entity
     * @return
     */
    @SelectProvider(type= BaseSelectProvider.class,method = "selectAll")
    List<T> baseSelectAll(T entity);

    /**
     * 带条件的查询，该查询为动态查询，不可缓存
     * 传入的对象中带@IndexAttribute注解的字段有值的都作为查询条件
     * 传入对象中带@SortAttribute注解的字段作为排序字段
     * @param entity 实体对象
     * param and 多个查询条件组合方式  true:AND  false:OR
     * param asc 排序方式  null:不指定排序方式  true:按指定排序字段升序   false:按指定排序字段降序
     * @return SELECT id,name... FROM router  WHERE name = #{name} AND serviceName = #{serviceName}  ORDER BY createTime ASC
     */
    @SelectProvider(type= BaseSelectProvider.class,method = "selectByCondition")
    List<T> baseSelectByCondition(T entity);


    /**
     * 查询记录总数
     * 返回的是 "SELECT COUNT(1) FROM 表名" 的结果
     * 不带查询条件
     * @param entity
     * @return
     */
    @SelectProvider(type = BaseSelectProvider.class,method = "selectCount")
    Integer baseSelectCount(T entity);

    /**
     * 根据条件查询记录总数
     * 传入的对象中带@IndexAttribute注解的字段有值的都作为查询条件
     * @param entity
     * @return SELECT COUNT(1) FROM router WHERE name = #{name} AND serviceName = #{serviceName}
     */
    @SelectProvider(type = BaseSelectProvider.class,method = "selectCountByCondition")
    Integer baseSelectCountByCondition(T entity);


    /**
     * 分页查询
     * 返回 “SELECT 所有字段 FROM 表名 LIMIT startRows,pageSize” 的结果
     * 不带查询条件
     * @param entity
     * @return
     */
    @SelectProvider(type = BaseSelectProvider.class,method = "selectPageList")
    List<T> baseSelectPageList(T entity);

    /**
     * 加条件的分页查询
     * 传入的对象中带@IndexAttribute注解的字段有值的都作为查询条件
     * @return SELECT id,name... FROM router  WHERE name = #{name} AND serviceName = #{serviceName}  ORDER BY createTime ASC LIMIT #{startRows},#{pageSize}
     */
    @SelectProvider(type = BaseSelectProvider.class,method = "selectPageListByCondition")
    List<T> baseSelectPageListByCondition(T entity);



}
