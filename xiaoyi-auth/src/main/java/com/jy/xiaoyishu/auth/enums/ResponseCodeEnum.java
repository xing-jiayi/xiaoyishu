package com.jy.xiaoyishu.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.lang.String;

import com.jy.framework.common.exception.BaseExceptionInterface;

/**
 *
 * @author ayi
 * @version V1.0
 * @title ResponseCodeEnum
 * @description 响应码枚举类
 * @date 2026/01/15 14:37
 */
@Getter
@AllArgsConstructor
public enum ResponseCodeEnum implements BaseExceptionInterface {
    // -------- 通用异常状态码 --------
    SYSTEM_ERROR("AUTH-10000", "系统错误"),
    PARAM_NOT_VALID("AUTH-10001", "参数错误"),
    VERIFICATION_CODE_SEND_FREQUENTLY("AUTH-20000", "请求太频繁，请3分钟后再试");

    /**
     * 错误码
     */
    private final String errorCode;
    /**
     * 错误信息
     */
    private final String errorMessage;
}
