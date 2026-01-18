package top.crushtj.xiaoyishu.auth.model.vo.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.crushtj.framework.common.validator.PhoneNumber;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author ayi
 * @version V1.0
 * @title UserVo
 * @date 2026/01/18 20:13
 * @description 用户登录参数
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginReqVO implements Serializable {
    @Serial
    private static final long serialVersionUID = -2349922278492431614L;
    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    @PhoneNumber
    private String phone;

    /**
     * 验证码
     */
    private String code;

    /**
     * 密码
     */
    private String password;

    /**
     * 登录类型：手机号验证码，或者是账号密码
     */
    @NotNull(message = "登录类型不能为空")
    private Integer type;
}
