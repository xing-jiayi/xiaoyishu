package top.crushtj.xiaoyishu.auth.model.vo.verificationcode;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.crushtj.framework.common.validator.PhoneNumber;

/**
 *
 * @author ayi
 * @version V1.0
 * @title SendVerificationCodeReqVO
 * @description 发送验证码请求参数
 * @date 2026/01/15 18:39
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendVerificationCodeReqVO {

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    @PhoneNumber
    private String phoneNumber;

}
