package top.crushtj.xiaoyishu.auth.runner;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Lists;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import top.crushtj.framework.common.utils.JsonUtils;
import top.crushtj.xiaoyishu.auth.constant.RedisKeyConstants;
import top.crushtj.xiaoyishu.auth.domain.entity.PermissionEntity;
import top.crushtj.xiaoyishu.auth.domain.entity.RoleEntity;
import top.crushtj.xiaoyishu.auth.domain.entity.RolePermissionRelEntity;
import top.crushtj.xiaoyishu.auth.domain.mappers.PermissionMapper;
import top.crushtj.xiaoyishu.auth.domain.mappers.RoleMapper;
import top.crushtj.xiaoyishu.auth.domain.mappers.RolePermissionRelMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 *
 * @author ayi
 * @version V1.0
 * @title PushRolePermissions2RedisRunner
 * @date 2026/01/23 16:51
 * @description 推送角色权限到Redis
 */

@Slf4j
@Component
public class PushRolePermissions2RedisRunner implements ApplicationRunner {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RolePermissionRelMapper rolePermissionRelMapper;

    @Resource
    private PermissionMapper permissionMapper;

    public static final String PUSH_PERMISSION_FLAG = "push.permission.flag";

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("==> 开始同步角色权限数据到 Redis 中...");

        try {
            // 是否能够同步数据: 原子操作，只有在键 PUSH_PERMISSION_FLAG 不存在时，才会设置该键的值为 "1"，并设置过期时间为 1 天
            boolean canPushed = Boolean.TRUE.equals(redisTemplate.opsForValue()
                .setIfAbsent(PUSH_PERMISSION_FLAG, "1", 1, TimeUnit.DAYS));
            // 如果无法同步权限数据
            if (!canPushed) {
                log.warn("==> 角色权限数据已经同步至 Redis 中，不再同步...");
                return;
            }
            // 查询所有已启用角色
            List<RoleEntity> roleList = roleMapper.selectEnabledRoleList();
            if (CollUtil.isNotEmpty(roleList)) {
                // 获取角色ID集合
                List<Long> roleIds = roleList.stream()
                    .map(RoleEntity::getId)
                    .toList();

                //查询角色对应的权限
                List<RolePermissionRelEntity> rolePermissionList = rolePermissionRelMapper.selectByRoleIds(roleIds);

                // 按角色 ID 分组, 每个角色 ID 对应多个权限 ID
                Map<Long, List<Long>> roleIdPermissionIdsMap = rolePermissionList.stream()
                    .collect(Collectors.groupingBy(RolePermissionRelEntity::getRoleId,
                        Collectors.mapping(RolePermissionRelEntity::getPermissionId, Collectors.toList())));

                // 查询所有启用的权限
                List<PermissionEntity> permissionList = permissionMapper.selectAppEnabledList();

                // 构建权限 ID 和权限 DO 对象的映射关系
                Map<Long, PermissionEntity> permissionIdEntityMap = permissionList.stream()
                    .collect(Collectors.toMap(PermissionEntity::getId, permissionEntity -> permissionEntity));

                // 构建角色 ID 和权限 DO 列表的映射关系
                Map<Long, List<PermissionEntity>> roleIdPermissionMap = new HashMap<>();
                roleList.forEach(role -> {
                    Long roleId = role.getId();
                    //获取角色对应的权限id列表
                    List<Long> permissionIds = roleIdPermissionIdsMap.get(roleId);
                    if (CollUtil.isNotEmpty(permissionIds)) {
                        List<PermissionEntity> perDOS = Lists.newArrayList();
                        permissionIds.forEach(permissionId -> {
                            // 根据权限 ID 获取具体的权限 DO 对象
                            PermissionEntity permissionDO = permissionIdEntityMap.get(permissionId);
                            if (Objects.nonNull(permissionDO)) {
                                perDOS.add(permissionDO);
                            }
                        });
                        roleIdPermissionMap.put(roleId, perDOS);
                    }
                });

                // 将角色 ID 和权限 DO 列表的映射关系写入 Redis
                roleIdPermissionMap.forEach((roleId, permission) -> {
                    String key = RedisKeyConstants.buildRolePermissionsKey(roleId);
                    redisTemplate.opsForValue()
                        .set(key, JsonUtils.toJsonString(permission));
                });
            }
            log.info("==> 角色权限数据同步到 Redis 完成...");
        } catch (Exception e) {
            log.error("==> 角色权限数据同步到 Redis 失败...", e);
        }

    }

}
