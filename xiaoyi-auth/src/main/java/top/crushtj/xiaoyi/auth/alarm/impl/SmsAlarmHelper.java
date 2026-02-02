package top.crushtj.xiaoyi.auth.alarm.impl;

import lombok.extern.slf4j.Slf4j;
import top.crushtj.xiaoyi.auth.alarm.AlarmInterface;

/**
 * @author ayi
 * @version V1.0
 * @title SmsAlarmHelper
 * @date 2026/2/2 15:39
 * @description 短信告警通知类
 */

@Slf4j
public class SmsAlarmHelper implements AlarmInterface {

    /**
     * 发送告警信息
     *
     * @param message 告警信息
     * @return 发送结果
     */
    @Override
    public boolean send(String message) {
        log.info("==> 【短信告警】：{}", message);
        return false;
    }
}
