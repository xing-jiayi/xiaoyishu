package top.crushtj.xiaoyishu.auth.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author ayi
 * @version V1.0
 * @title UserEntity
 * @date 2026-01-19 19:49:50
 * @description 用户表(t_user)表实体类
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_user")
public class UserEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -47473970233354078L;

    /**
     * 主键ID
     */
    @TableId("ID")
    private Long id;

    /**
     * 小壹书号(唯一凭证)
     */
    @TableField("XIAOYISHU_ID")
    private String xiaoyishuId;

    /**
     * 密码
     */
    @TableField("PASSWORD")
    private String password;

    /**
     * 昵称
     */
    @TableField("NICKNAME")
    private String nickname;

    /**
     * 头像
     */
    @TableField("AVATAR")
    private String avatar;

    /**
     * 生日
     */
    @TableField("BIRTHDAY")
    private LocalDate birthday;

    /**
     * 背景图
     */
    @TableField("BACKGROUND_IMG")
    private String backgroundImg;

    /**
     * 手机号
     */
    @TableField("PHONE")
    private String phone;

    /**
     * 性别(1: 男 2: 女 3: 未知)
     */
    @TableField("SEX")
    private Integer sex;

    /**
     * 状态(1：启用 0：禁用)
     */
    @TableField("STATUS")
    private Integer status;

    /**
     * 个人简介
     */
    @TableField("INTRODUCTION")
    private String introduction;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField("UPDATE_TIME")
    private LocalDateTime updateTime;

    /**
     * 逻辑删除(0：未删除 1：已删除)
     */
    @TableField("IS_DELETED")
    private Boolean isDeleted;

}
