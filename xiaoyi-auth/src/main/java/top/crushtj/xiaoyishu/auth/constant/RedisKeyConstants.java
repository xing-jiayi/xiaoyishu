package top.crushtj.xiaoyishu.auth.constant;

/**
 *
 * @author ayi
 * @version V1.0
 * @title RedisKeyConstants
 * @description Redis key 常量
 * @date 2026/01/15 18:45
 */

public class RedisKeyConstants {

    /**
     * 验证码 KEY 前缀
     */
    private static final String VERIFICATION_CODE_KEY_PREFIX = "verification_code:";
    /**
     * 验证码 KEY 过期时间 (分钟)
     */
    public static final long VERIFICATION_CODE_EXPIRE_TIME = 3;

    /**
     * 用户角色数据 KEY 前缀
     */
    private static final String USER_ROLES_KEY_PREFIX = "user:roles:";

    /**
     * 小壹书全局 ID 生成器 KEY
     */
    public static final String XIAOYI_ID_GENERATOR_KEY = "xiaoyishu.id.generator";

    /**
     * 角色权限数据 KEY 前缀
     */
    public static final String ROLE_PERMISSIONS_KEY_PREFIX = "role:permissions:";

    /**
     * 构建验证码 KEY
     *
     * @param phone 手机号
     * @return 验证码 KEY
     */
    public static String buildVerificationCodeKey(String phone) {
        return VERIFICATION_CODE_KEY_PREFIX + phone;
    }

    /**
     * 构建用户角色数据 KEY
     *
     * @param phone 用户手机号
     * @return 用户角色数据 KEY
     */
    public static String buildUserRolesKey(String phone) {
        return USER_ROLES_KEY_PREFIX + phone;
    }

    /**
     * 构建角色权限数据 KEY
     *
     * @param roleId 角色 ID
     * @return 角色权限数据 KEY
     */
    public static String buildRolePermissionsKey(Long roleId) {
        return ROLE_PERMISSIONS_KEY_PREFIX + roleId;
    }

}
