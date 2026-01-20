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
import java.time.LocalDateTime;

/**
 * @author ayi
 * @version V1.0
 * @title UserRoleRelEntity
 * @date 2026-01-19 19:49:59
 * @description 用户角色表(t_user_role_rel)表实体类
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_user_role_rel")
public class UserRoleRelEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -77498437697772085L;

    /**
     * 主键ID
     */
    @TableId("ID")
    private Long id;

    /**
     * 用户ID
     */
    @TableField("USER_ID")
    private Long userId;

    /**
     * 角色ID
     */
    @TableField("ROLE_ID")
    private Long roleId;

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
