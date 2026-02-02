package top.crushtj.xiaoyi.auth.service;

import top.crushtj.framework.common.response.Response;
import top.crushtj.xiaoyi.auth.model.vo.verificationcode.SendVerificationCodeReqVO;

/**
 *
 * @author ayi
 * @version V1.0
 * @title VerificationCodeService
 * @description 验证码服务
 * @date 2026/01/15 18:48
 */

public interface VerificationCodeService {

    /**
     * 发送验证码
     *
     * @param sendVerificationCodeReqVO 发送验证码请求参数
     * @return 响应结果
     */
    Response<?> send(SendVerificationCodeReqVO sendVerificationCodeReqVO);

}
