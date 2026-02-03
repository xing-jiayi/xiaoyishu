package top.crushtj.xiaoyi.gateway.constant;

/**
 * @author ayi
 * @version V1.0
 * @title RedisKeyConstants
 * @date 2026/2/3 15:24
 * @description Redis key 常量
 */

public class RedisKeyConstants {

    /**
     * 用户对应角色集合 KEY 前缀
     */
    private static final String USER_ROLES_KEY_PREFIX = "user:roles:";

    /**
     * 角色对应的权限集合 KEY 前缀
     */
    private static final String ROLE_PERMISSIONS_KEY_PREFIX = "role:permissions:";

    /**
     * 构建角色对应的权限集合 KEY
     *
     * @param roleKey 角色 key
     * @return 角色对应的权限集合 KEY
     */
    public static String buildRolePermissionsKey(String roleKey) {
        return ROLE_PERMISSIONS_KEY_PREFIX + roleKey;
    }

    /**
     * 构建用户-角色 KEY
     *
     * @param userId 用户 ID
     * @return 用户-角色 KEY
     */
    public static String buildUserRoleKey(Long userId) {
        return USER_ROLES_KEY_PREFIX + userId;
    }
}
