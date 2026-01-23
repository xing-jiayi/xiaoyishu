package top.crushtj.xiaoyishu.auth.domain.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.crushtj.xiaoyishu.auth.domain.entity.RoleEntity;

import java.util.List;

/**
 * @author ayi
 * @version V1.0
 * @title RoleMapper
 * @date 2026-01-19 19:48:24
 * @description 角色表(t_role)表数据库访问层
 */
public interface RoleMapper extends BaseMapper<RoleEntity> {

    /**
     * 查询所有启用的角色列表
     *
     * @return 角色列表
     */
    List<RoleEntity> selectEnabledRoleList();

}

