package top.crushtj.xiaoyishu.auth.domain.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.crushtj.xiaoyishu.auth.domain.entity.PermissionEntity;

import java.util.List;

/**
 * @author ayi
 * @version V1.0
 * @title PermissionMapper
 * @date 2026-01-19 19:47:28
 * @description 权限表(t_permission)表数据库访问层
 */
public interface PermissionMapper extends BaseMapper<PermissionEntity> {

    /**
     * 查询所有已启用的权限列表
     *
     * @return 已启用的权限列表
     */
    List<PermissionEntity> selectAppEnabledList();

}

