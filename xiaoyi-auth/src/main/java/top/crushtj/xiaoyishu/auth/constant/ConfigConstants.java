package top.crushtj.xiaoyishu.auth.constant;

/**
 * @author ayi
 * @version V1.0
 * @title ConfigConstants
 * @date 2026/01/16
 * @description 配置常量类
 */

public class ConfigConstants {

    /**
     * 核心线程数
     */
    public static final int CORE_POOL_SIZE = 10;
    /**
     * 最大线程数
     */
    public static final int MAX_POOL_SIZE = 50;
    /**
     * 队列容量
     */
    public static final int QUEUE_CAPACITY = 200;
    /**
     * 线程活跃时间（秒）
     */
    public static final int KEEP_ALIVE_SECONDS = 30;
    /**
     * 线程名前缀
     */
    public static final String THREAD_NAME_PREFIX = "AuthExecutor-";

    public static final int AWAIT_TERMINATION_SECONDS = 60;
}
