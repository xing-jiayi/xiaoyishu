package top.crushtj.framework.biz.operationlog.aspect;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author ayi
 * @title ApiOperationLog
 * @description 自定义注解，用于标记需要记录操作日志的方法
 * @date 2025/11/21
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface ApiOperationLog {
    /**
     * API 功能描述
     *
     * @return 功能描述
     */
    String description() default "";
}
