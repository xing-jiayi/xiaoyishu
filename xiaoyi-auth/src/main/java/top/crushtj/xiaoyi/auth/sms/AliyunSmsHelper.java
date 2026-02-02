package top.crushtj.xiaoyi.auth.sms;

import com.aliyun.dypnsapi20170525.models.SendSmsVerifyCodeResponse;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.crushtj.framework.common.utils.JsonUtils;
import top.crushtj.framework.common.utils.MaskUtils;

/**
 * @author ayi
 * @version V1.0
 * @title AliyunSmsHelper
 * @date 2026/01/16 23:36
 * @description 阿里云短信工具
 */

@Slf4j
@Component
public class AliyunSmsHelper {

    @Resource
    private com.aliyun.dypnsapi20170525.Client client;

    public boolean sendMessage(String signName, String templateCode, String phoneNumber, String templateParam) {
        com.aliyun.dypnsapi20170525.models.SendSmsVerifyCodeRequest sendSmsVerifyCodeRequest = new com.aliyun.dypnsapi20170525.models.SendSmsVerifyCodeRequest()
            .setSignName(signName)
            .setTemplateCode(templateCode)
            .setPhoneNumber(phoneNumber)
            .setTemplateParam(templateParam);

        com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();

        try {
            log.info("==> 开始短信发送, phone: {}, signName: {}, templateCode: {}, templateParam: {}", MaskUtils.maskMobile(phoneNumber), signName, templateCode, templateParam);

            // 发送短信
            SendSmsVerifyCodeResponse response = client.sendSmsVerifyCodeWithOptions(sendSmsVerifyCodeRequest, runtime);

            boolean success = response.getBody().success;
            log.info("==> 短信发送状态: {}, response: {}", success, JsonUtils.toJsonString(response));
            return success;
        } catch (Exception error) {
            log.error("==> 短信发送错误: ", error);
            return false;
        }
    }

}
