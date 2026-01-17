package top.crushtj.xiaoyishu.auth.sms;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author ayi
 * @version V1.0
 * @title AliyunAccessKeyProperties
 * @date 2026/01/16 23:31
 * @description 阿里云访问密钥配置
 */

@ConfigurationProperties(prefix = "aliyun")
@Component
@Data
public class AliyunAccessKeyProperties {
    private String accessKeyId;
    private String accessKeySecret;
}
