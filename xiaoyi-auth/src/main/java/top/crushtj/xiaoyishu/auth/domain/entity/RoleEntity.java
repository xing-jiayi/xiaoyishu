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
 * @title RoleEntity
 * @date 2026-01-19 19:48:23
 * @description 角色表(t_role)表实体类
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_role")
public class RoleEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -77681371692201000L;

    /**
     * 主键ID
     */
    @TableId("ID")
    private Long id;

    /**
     * 角色名
     */
    @TableField("ROLE_NAME")
    private String roleName;

    /**
     * 角色唯一标识
     */
    @TableField("ROLE_KEY")
    private String roleKey;

    /**
     * 状态(1：启用 0：禁用)
     */
    @TableField("STATUS")
    private Integer status;

    /**
     * 管理系统中的显示顺序
     */
    @TableField("SORT")
    private Integer sort;

    /**
     * 备注
     */
    @TableField("REMARK")
    private String remark;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

    /**
     * 最后一次更新时间
     */
    @TableField("UPDATE_TIME")
    private LocalDateTime updateTime;

    /**
     * 逻辑删除(0：未删除 1：已删除)
     */
    @TableField("IS_DELETED")
    private Boolean isDeleted;

}
