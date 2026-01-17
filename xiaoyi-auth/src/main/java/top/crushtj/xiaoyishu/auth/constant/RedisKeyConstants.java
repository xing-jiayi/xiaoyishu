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
     * 构建验证码 KEY
     * 
     * @param phone 手机号
     * @return 验证码 KEY
     */
    public static String buildVerificationCodeKey(String phone) {
        return VERIFICATION_CODE_KEY_PREFIX + phone;
    }

}
