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
 * @title RolePermissionRelEntity
 * @date 2026-01-18 21:21:12
 * @description 用户权限表(t_role_permission_rel)表实体类
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_role_permission_rel")
public class RolePermissionRelEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 308157477991435934L;

    /**
     * 主键ID
     */
    @TableId("ID")
    private Long id;

    /**
     * 角色ID
     */
    @TableField("ROLE_ID")
    private Long roleId;

    /**
     * 权限ID
     */
    @TableField("PERMISSION_ID")
    private Long permissionId;

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
    private Integer isDeleted;

}
