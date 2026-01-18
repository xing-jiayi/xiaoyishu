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
 * @title PermissionEntity
 * @date 2026-01-18 21:20:36
 * @description 权限表(t_permission)表实体类
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_permission")
public class PermissionEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -18586083527537804L;

    /**
     * 主键ID
     */
    @TableId("ID")
    private Long id;

    /**
     * 父ID
     */
    @TableField("PARENT_ID")
    private Long parentId;

    /**
     * 权限名称
     */
    @TableField("NAME")
    private String name;

    /**
     * 类型(1：目录 2：菜单 3：按钮)
     */
    @TableField("TYPE")
    private Integer type;

    /**
     * 菜单路由
     */
    @TableField("MENU_URL")
    private String menuUrl;

    /**
     * 菜单图标
     */
    @TableField("MENU_ICON")
    private String menuIcon;

    /**
     * 管理系统中的显示顺序
     */
    @TableField("SORT")
    private Integer sort;

    /**
     * 权限标识
     */
    @TableField("PERMISSION_KEY")
    private String permissionKey;

    /**
     * 状态(1：启用；0：禁用)
     */
    @TableField("STATUS")
    private Integer status;

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
