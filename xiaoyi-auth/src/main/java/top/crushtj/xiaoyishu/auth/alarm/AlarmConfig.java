package top.crushtj.xiaoyishu.auth.alarm;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.crushtj.xiaoyishu.auth.alarm.impl.MailAlarmHelper;
import top.crushtj.xiaoyishu.auth.alarm.impl.SmsAlarmHelper;
import top.crushtj.xiaoyishu.auth.constant.XiaoyiAuthConstants;

/**
 * @author ayi
 * @version V1.0
 * @title AlarmConfig
 * @date 2026/2/2 15:37
 * @description 告警配置类
 */

@Configuration
@RefreshScope
public class AlarmConfig {

    @Value("${alarm.type}")
    private String alarmType;

    @Bean
    @RefreshScope
    public AlarmInterface alarmHelper() {
        // 根据配置文件中的告警类型，初始化选择不同的告警实现类
        if (StringUtils.equals(XiaoyiAuthConstants.ALARM_TYPE_SMS, alarmType)) {
            return new SmsAlarmHelper();
        } else if (StringUtils.equals(XiaoyiAuthConstants.ALARM_TYPE_EMAIL, alarmType)) {
            return new MailAlarmHelper();
        } else {
            throw new IllegalArgumentException("错误的告警类型...");
        }
    }
}
