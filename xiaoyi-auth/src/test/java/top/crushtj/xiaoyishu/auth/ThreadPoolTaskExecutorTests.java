package top.crushtj.xiaoyishu.auth;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author ayi
 * @version V1.0
 * @title ThreadPoolTaskExecutorTests
 * @date 2026/01/16 22:34
 * @description 线程池测试类
 */

@Slf4j
@SpringBootTest
public class ThreadPoolTaskExecutorTests {
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    /**
     * 测试线程池
     */
    @Test
    void testSubmit() {
        int count = 300;
        while (count-- > 0) {
            int finalCount = count;
            threadPoolTaskExecutor.submit(() -> log.info("异步线程: 这是{}号线程", finalCount));
        }
    }
}
