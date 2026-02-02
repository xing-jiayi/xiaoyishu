package top.crushtj.xiaoyishu.auth.alarm;

/**
 * @author ayi
 * @version V1.0
 * @title AlarmInterface
 * @date 2026/2/2 15:37
 * @description 告警接口
 */

public interface AlarmInterface {

    /**
     * 发送告警信息
     *
     * @param message 告警信息
     * @return 发送结果
     */
    boolean send(String message);
}
