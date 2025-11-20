package top.crushtj.framework.common.response;

import java.io.Serializable;

import lombok.Data;
import top.crushtj.framework.common.exception.BaseExceptionInterface;
import top.crushtj.framework.common.exception.BizException;

/**
 *
 * @Title: Response
 * @Description: 响应体
 * @Author: ayi
 * @Date: 2025/11/20
 */

@Data
public class Response<T> implements Serializable {


  private static final long serialVersionUID = -6624218097474846897L;

  /**
   * 响应码
   */
  private String code;

  /**
   * 响应信息
   */
  private String message;

  /**
   * 是否成功
   */
  private boolean success = true;

  /**
   * 响应数据
   */
  private T data;

  /**
   * 成功响应
   * 
   * @return 成功响应
   */
  public static <T> Response<T> success() {
    return new Response<>();
  }

  /**
   * 成功响应
   * 
   * @param data 响应数据
   * @return 成功响应
   */
  public static <T> Response<T> success(T data) {
    Response<T> response = new Response<>();
    response.setData(data);
    return response;
  }

  /**
   * 失败响应
   * 
   * @return 失败响应
   */
  public static <T> Response<T> failure() {
    Response<T> response = new Response<>();
    response.setSuccess(false);
    return response;
  }

  /**
   * 失败响应
   * 
   * @param message 异常信息
   * @return 失败响应
   */
  public static <T> Response<T> failure(String message) {
    Response<T> response = new Response<>();
    response.setSuccess(false);
    response.setMessage(message);
    return response;
  }

  /**
   * 失败响应
   * 
   * @param code    异常码
   * @param message 异常信息
   * @return 失败响应
   */
  public static <T> Response<T> failure(String code, String message) {
    Response<T> response = new Response<>();
    response.setSuccess(false);
    response.setCode(code);
    response.setMessage(message);
    return response;
  }

  /**
   * 失败响应
   * 
   * @param bizException 业务异常
   * @return 失败响应
   */
  public static <T> Response<T> failure(BizException bizException) {
    Response<T> response = new Response<>();
    response.setSuccess(false);
    response.setCode(bizException.getCode());
    response.setMessage(bizException.getMessage());
    return response;
  }

  /**
   * 失败响应
   * 
   * @param <T>
   * @param baseExceptionInterface 基础异常
   * @return 失败响应
   */
  public static <T> Response<T> failure(BaseExceptionInterface baseExceptionInterface) {
    Response<T> response = new Response<>();
    response.setSuccess(false);
    response.setCode(baseExceptionInterface.getCode());
    response.setMessage(baseExceptionInterface.getMessage());
    return response;
  }
}
