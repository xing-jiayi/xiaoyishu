package top.crushtj.xiaoyi.auth.domain.mappers;

import org.apache.ibatis.annotations.Param;
import top.crushtj.xiaoyi.auth.domain.entity.RolePermissionRelEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author ayi
 * @version V1.0
 * @title RolePermissionRelMapper
 * @date 2026-01-19 19:48:32
 * @description 用户权限表(t_role_permission_rel)表数据库访问层
 */
public interface RolePermissionRelMapper extends BaseMapper<RolePermissionRelEntity> {

    /**
     * 根据角色ID查询权限
     *
     * @param roleIds 角色ID列表
     * @return 权限列表
     */
    List<RolePermissionRelEntity> selectByRoleIds(@Param("roleIds") List<Long> roleIds);

}

