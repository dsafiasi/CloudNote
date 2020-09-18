package zihong.wu.cloudnote.common.mybatis.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 数据库主键注释
 * 1. 仅支持单字段作为主键使用，将该字段上添加该注解即可
 * 2. 当使用id字段作为主键时，不需要添加该注解
 * 3. BaseMapper里后缀为ById的方法均是依照id字段作为主键设计的
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface KeyAttribute {
    /**
     * 是否是自增主键
     * @return
     */
    boolean autoIncr() default false;
}
