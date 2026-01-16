package top.crushtj.xiaoyishu.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import static top.crushtj.xiaoyishu.auth.constant.ConfigConstants.*;

/**
 * @author ayi
 * @version V1.0
 * @title ThreadPoolConfig
 * @date 2026/01/16 22:26
 * @description 线程池配置类
 */

@Configuration
public class ThreadPoolConfig {

    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数
        executor.setCorePoolSize(CORE_POOL_SIZE);
        // 最大线程数
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        // 队列容量
        executor.setQueueCapacity(QUEUE_CAPACITY);
        // 线程活跃时间（秒）
        executor.setKeepAliveSeconds(KEEP_ALIVE_SECONDS);
        // 线程名前缀
        executor.setThreadNamePrefix(THREAD_NAME_PREFIX);

        // 拒绝策略：由调用线程处理（一般为主线程）
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 设置等待时间，如果超过这个时间还没有销毁就强制销毁，以确保应用最后能够被关闭，而不是被没有完成的任务阻塞
        executor.setAwaitTerminationSeconds(AWAIT_TERMINATION_SECONDS);

        executor.initialize();
        return executor;
    }
}
