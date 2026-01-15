package com.jy.framework.common.exception;

/**
 *
 * @author ayi
 * @title BaseExceptionInterface
 * @description 基础异常接口
 * @date 2025/11/20
 */

public interface BaseExceptionInterface {
    /**
     * 获取异常码
     *
     * @return 异常码
     */
    String getErrorCode();

    /**
     * 获取异常信息
     *
     * @return 异常信息
     */
    String getErrorMessage();

}
