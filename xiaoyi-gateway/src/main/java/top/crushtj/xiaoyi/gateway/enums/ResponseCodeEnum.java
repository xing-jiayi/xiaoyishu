package top.crushtj.xiaoyi.gateway.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import top.crushtj.framework.common.exception.BaseExceptionInterface;

/**
 * @author ayi
 * @version V1.0
 * @title ResponseCodeEnum
 * @date 2026/2/3 16:15
 * @description 异常状态吗枚举
 */

@Getter
@AllArgsConstructor
public enum ResponseCodeEnum implements BaseExceptionInterface {
    // ----------- 通用异常状态码 -----------
    SYSTEM_ERROR("500", "系统繁忙，请稍后再试"),
    UNAUTHORIZED("401", "没有权限"),

    // ----------- 业务异常状态码 -----------
    ;

    // 异常码
    private final String errorCode;
    // 错误信息
    private final String errorMessage;
}
