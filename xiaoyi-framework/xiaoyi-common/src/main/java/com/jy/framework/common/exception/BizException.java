package com.jy.framework.common.exception;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ayi
 * @title BusiException
 * @description 业务异常
 * @date 2025/11/20
 */

@Getter
@Setter
public class BizException extends RuntimeException {

    /**
     * 异常码
     */
    private String errorCode;

    /**
     * 异常信息
     */
    private String errorMessage;

    /**
     * 构造函数
     *
     * @param baseExceptionInterface 基础异常接口
     */
    public BizException(BaseExceptionInterface baseExceptionInterface) {
        this.errorCode = baseExceptionInterface.getErrorCode();
        this.errorMessage = baseExceptionInterface.getErrorMessage();
    }

}
