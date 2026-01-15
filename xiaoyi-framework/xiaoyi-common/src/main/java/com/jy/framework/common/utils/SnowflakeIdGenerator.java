package com.jy.framework.common.utils;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;

public class SnowflakeIdGenerator {
    // ====================== 配置参数 ======================
    /** 开始时间戳 (2024-01-01 00:00:00)，可自定义，减少ID长度 */
    private static final long START_TIMESTAMP = 1704067200000L;

    /** 机器ID所占的位数 (最多10位，支持1024个节点) */
    private static final long WORKER_ID_BITS = 10L;

    /** 序列号所占的位数 (最多12位，支持4096个/毫秒) */
    private static final long SEQUENCE_BITS = 12L;

    // ====================== 位移计算 ======================
    /** 机器ID左移位数 (12位) */
    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;

    /** 时间戳左移位数 (10+12=22位) */
    private static final long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;

    /** 序列号的最大值 (4095) */
    private static final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);

    // ====================== 全局变量 ======================
    /** 机器ID (0-1023) */
    private final long workerId;

    /** 序列号 (0-4095) */
    private long sequence = 0L;

    /** 上一次生成ID的时间戳 */
    private long lastTimestamp = -1L;

    // ====================== 单例实例 ======================
    private static volatile SnowflakeIdGenerator INSTANCE;

    /**
     * 私有构造器，初始化机器ID（自动计算，也可手动指定）
     * 
     * @param workerId 机器ID (0-1023)
     * @throws IllegalArgumentException 机器ID超出范围时抛出
     */
    private SnowflakeIdGenerator(long workerId) {
        // 校验机器ID范围
        long maxWorkerId = ~(-1L << WORKER_ID_BITS);
        if (workerId < 0 || workerId > maxWorkerId) {
            throw new IllegalArgumentException(String.format("Worker ID 必须在 0 到 %d 之间", maxWorkerId));
        }
        this.workerId = workerId;
    }

    /**
     * 获取单例实例（自动计算机器ID）
     * 
     * @return 雪花算法生成器实例
     */
    public static SnowflakeIdGenerator getInstance() {
        if (INSTANCE == null) {
            synchronized (SnowflakeIdGenerator.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SnowflakeIdGenerator(calculateWorkerId());
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 手动指定机器ID获取实例（适合分布式部署时手动分配节点ID）
     * 
     * @param workerId 机器ID (0-1023)
     * @return 雪花算法生成器实例
     */
    public static SnowflakeIdGenerator getInstance(long workerId) {
        if (INSTANCE == null) {
            synchronized (SnowflakeIdGenerator.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SnowflakeIdGenerator(workerId);
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 生成下一个ID（核心方法，线程安全）
     * 
     * @return 有序、唯一的Long类型ID
     * @throws RuntimeException 时钟回拨时抛出（避免ID重复）
     */
    public synchronized long nextId() {
        long currentTimestamp = System.currentTimeMillis();

        // 检查时钟回拨（关键：避免ID重复）
        if (currentTimestamp < lastTimestamp) {
            throw new RuntimeException(
                String.format("时钟回拨检测！拒绝生成ID，上一次时间：%d，当前时间：%d", lastTimestamp, currentTimestamp));
        }

        // 同一毫秒内，序列号递增
        if (currentTimestamp == lastTimestamp) {
            sequence = (sequence + 1) & SEQUENCE_MASK;
            // 同一毫秒内序列号用尽，等待下一毫秒
            if (sequence == 0) {
                currentTimestamp = waitNextMillis(lastTimestamp);
            }
        } else {
            // 不同毫秒，序列号重置为0
            sequence = 0L;
        }

        // 更新最后生成ID的时间戳
        lastTimestamp = currentTimestamp;

        // 组合ID：时间戳 + 机器ID + 序列号
        return ((currentTimestamp - START_TIMESTAMP) << TIMESTAMP_LEFT_SHIFT) | (workerId << WORKER_ID_SHIFT)
            | sequence;
    }

    /**
     * 等待下一毫秒，直到获取新的时间戳
     * 
     * @param lastTimestamp 上一次生成ID的时间戳
     * @return 新的时间戳
     */
    private long waitNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

    /**
     * 自动计算机器ID（基于网卡+进程ID，避免手动配置）
     * 
     * @return 0-1023之间的机器ID
     */
    private static long calculateWorkerId() {
        try {
            // 第一步：获取网卡MAC地址的哈希值
            InetAddress address = InetAddress.getLocalHost();
            long macHash = NetworkInterface.getByInetAddress(address).getHardwareAddress()[0] & 0xFF;

            // 第二步：获取进程ID（避免同一机器多进程冲突）
            String processName = ManagementFactory.getRuntimeMXBean().getName();
            long pid = Long.parseLong(processName.split("@")[0]);

            // 组合并取模，确保在0-1023之间
            return (macHash + pid) % (~(-1L << WORKER_ID_BITS));
        } catch (Exception e) {
            // 异常时返回随机数（生产环境建议手动指定机器ID）
            return (long)(Math.random() * (~(-1L << WORKER_ID_BITS)));
        }
    }

    // ====================== 测试用例 ======================
    public static void main(String[] args) {
        // 测试生成10个ID，验证有序性和唯一性
        SnowflakeIdGenerator generator = SnowflakeIdGenerator.getInstance();
        for (int i = 0; i < 10; i++) {
            long id = generator.nextId();
            System.out.println("生成的ID：" + id);
        }

        // 多线程测试（验证线程安全）
        Runnable task = () -> {
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + " -> " + generator.nextId());
            }
        };
        new Thread(task, "线程1").start();
        new Thread(task, "线程2").start();
    }

}
