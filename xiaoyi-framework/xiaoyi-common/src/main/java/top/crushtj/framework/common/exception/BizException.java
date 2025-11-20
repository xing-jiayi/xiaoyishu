package top.crushtj.framework.common.exception;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @Title: BusiException
 * @Description: 业务异常
 * @Author: ayi
 * @Date: 2025/11/20
 */

@Getter
@Setter
public class BizException extends RuntimeException {

  /**
   * 异常码
   */
  private String code;

  /**
   * 异常信息
   */
  private String message;

  /**
   * 构造函数
   * 
   * @param baseExceptionInterface 基础异常接口
   */
  public BizException(BaseExceptionInterface baseExceptionInterface) {
    this.code = baseExceptionInterface.getCode();
    this.message = baseExceptionInterface.getMessage();
  }

}
