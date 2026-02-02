package top.crushtj.xiaoyi.auth.exception;

import java.util.Optional;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import top.crushtj.framework.common.exception.BizException;
import top.crushtj.framework.common.response.Response;
import top.crushtj.xiaoyi.auth.enums.ResponseCodeEnum;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author ayi
 * @version V1.0
 * @title GlobalExceptionHandler
 * @description 全局异常处理器
 * @date 2026/01/15 18:40
 */

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 捕获自定义业务异常
     * 
     * @param request 请求
     * @param e 异常
     * @return 响应结果
     */
    @ExceptionHandler({BizException.class})
    @ResponseBody
    public Response<Object> handleBizException(HttpServletRequest request, BizException e) {
        log.warn("{} request failure, errorCode: {}, errorMessage: {}", request.getRequestURI(), e.getErrorCode(),
            e.getErrorMessage());
        return Response.failure(e);
    }

    /**
     * 捕获参数校验异常
     * 
     * @param request 请求
     * @param e 异常
     * @return 响应结果
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public Response<Object> handleMethodArgumentNotValidException(HttpServletRequest request,
        MethodArgumentNotValidException e) {
        // 参数错误异常码
        String errorCode = ResponseCodeEnum.PARAM_NOT_VALID.getErrorCode();

        // 获取 BindingResult
        BindingResult bindingResult = e.getBindingResult();

        StringBuilder sb = new StringBuilder();

        // 获取校验不通过的字段，并组合错误信息，格式为： email 邮箱格式不正确, 当前值: '123124qq.com';
        Optional.of(bindingResult.getFieldErrors()).ifPresent(errors -> {
            errors.forEach(error -> sb.append(error.getField()).append(" ").append(error.getDefaultMessage())
                .append(", 当前值: '").append(error.getRejectedValue()).append("'; ")

            );
        });

        // 错误信息
        String errorMessage = sb.toString();

        log.warn("{} request error, errorCode: {}, errorMessage: {}", request.getRequestURI(), errorCode, errorMessage);

        return Response.failure(errorCode, errorMessage);
    }

    /**
     * 其他类型异常
     * 
     * @param request 请求
     * @param e 异常
     * @return 响应结果
     */
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public Response<Object> handleOtherException(HttpServletRequest request, Exception e) {
        log.error("{} request error, ", request.getRequestURI(), e);
        return Response.failure(ResponseCodeEnum.SYSTEM_ERROR);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseBody
    public Response<Object> handleIllegalArgumentException(HttpServletRequest request, IllegalArgumentException e) {
        log.error("{} request error, ", request.getRequestURI(), e);
        String errorCode = ResponseCodeEnum.PARAM_NOT_VALID.getErrorCode();
        String errorMessage = e.getMessage();
        return Response.failure(errorCode,errorMessage);
    }
}
