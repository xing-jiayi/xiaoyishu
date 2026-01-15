package com.jy.xiaoyishu.auth.controller;

import com.jy.framework.biz.operationlog.aspect.ApiOperationLog;
import com.jy.framework.common.response.Response;
import com.jy.xiaoyishu.auth.model.vo.verificationcode.SendVerificationCodeReqVO;
import com.jy.xiaoyishu.auth.service.VerificationCodeService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ayi
 * @version V1.0
 * @title VerificationCodeController
 * @date 2026/01/15
 * @description 验证码控制器
 */

@Slf4j
@RestController
public class VerificationCodeController {
    @Resource
    private VerificationCodeService verificationCodeService;

    @PostMapping("/verification/code/send")
    @ApiOperationLog(description = "发送短信验证码")
    public Response<?> send(@Validated @RequestBody SendVerificationCodeReqVO sendVerificationCodeReqVO) {
        return verificationCodeService.send(sendVerificationCodeReqVO);
    }
}
