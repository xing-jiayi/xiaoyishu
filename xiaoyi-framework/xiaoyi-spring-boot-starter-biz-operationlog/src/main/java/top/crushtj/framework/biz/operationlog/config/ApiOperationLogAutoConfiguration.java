package top.crushtj.framework.biz.operationlog.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

import top.crushtj.framework.biz.operationlog.ApiOperationLogAspect;

/**
 *
 * @author ayi
 * @title ApiOperationLog
 * @description 自定义注解，用于标记需要记录操作日志的方法
 * @date 2025/11/21
 */

@AutoConfiguration
public class ApiOperationLogAutoConfiguration {

    @Bean
    public ApiOperationLogAspect apiOperationLogAspect() {
        return new ApiOperationLogAspect();
    }
}
