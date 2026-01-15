package com.jy.xiaoyishu.auth.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 *
 * @author ayi
 * @title User
 * @description 测试用户类
 * @date 2025/11/26
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @NotNull(message = "用户ID不能为空")
    private Long id;
    @NotBlank(message = "用户名称不能为空")
    private String name;
    private Integer age;
    private LocalDateTime createTime;

}
