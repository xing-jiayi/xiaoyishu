package top.crushtj.framework.common.exception;

/**
 *
 * @Title: BaseExceptionInterface
 * @Description: 基础异常接口
 * @Author: ayi
 * @Date: 2025/11/20
 */

public interface BaseExceptionInterface {
  /**
   * 获取异常码
   * 
   * @return 异常码
   */
  String getCode();

  /**
   * 获取异常信息
   * 
   * @return 异常信息
   */
  String getMessage();

}
