package top.crushtj.framework.common.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author ayi
 * @version V1.0
 * @title MaskUtils
 * @date 2026/01/17 13:22
 * @description 数据脱敏工具类
 */

public class MaskUtils {

    // ===================== 通用脱敏方法（基础） =====================
    /**
     * 通用脱敏方法：保留前prefixLen位，后suffixLen位，中间用*填充
     * @param content 原始字符串
     * @param prefixLen 保留前缀长度
     * @param suffixLen 保留后缀长度
     * @return 脱敏后的字符串
     */
    public static String maskGeneral(String content, int prefixLen, int suffixLen) {
        // 空值处理：null/空串直接返回，避免NPE
        if (StringUtils.isBlank(content)) {
            return content;
        }
        int contentLen = content.length();
        // 若长度小于等于前缀+后缀，直接返回原字符串（避免过度脱敏）
        if (contentLen <= prefixLen + suffixLen) {
            return content;
        }
        // 拼接前缀 + 中间* + 后缀
        StringBuilder sb = new StringBuilder();
        // 添加前缀
        sb.append(content.substring(0, prefixLen));
        // 添加中间掩码（长度=总长度-前缀-后缀）
        sb.append("*".repeat(Math.max(0, contentLen - prefixLen - suffixLen)));
        // 添加后缀
        sb.append(content.substring(contentLen - suffixLen));
        return sb.toString();
    }

    // ===================== 常用敏感字段脱敏方法（业务封装） =====================

    /**
     * 手机号脱敏：保留前3位，后4位，中间4位掩码（如：138****1234）
     * @param mobile 手机号（支持11位常规手机号）
     * @return 脱敏后的手机号
     */
    public static String maskMobile(String mobile) {
        // 先校验手机号格式（简单校验，可根据业务调整）
        if (StringUtils.isBlank(mobile) || !mobile.matches("^1[3-9]\\d{9}$")) {
            return mobile;
        }
        return maskGeneral(mobile, 3, 4);
    }

    /**
     * 邮箱脱敏：保留前缀前3位，@及域名完整，中间掩码（如：123****@qq.com）
     * @param email 邮箱地址
     * @return 脱敏后的邮箱
     */
    public static String maskEmail(String email) {
        if (StringUtils.isBlank(email) || !email.contains("@")) {
            return email;
        }
        String[] parts = email.split("@");
        String prefix = parts[0];
        String domain = parts[1];
        // 前缀长度<=3时不脱敏，否则保留前3位
        String maskedPrefix = prefix.length() <= 3 ? prefix : maskGeneral(prefix, 3, 0);
        return maskedPrefix + "@" + domain;
    }

    /**
     * 姓名脱敏：
     * - 单字名：直接返回（如：李 → 李）
     * - 两字名：保留姓，名掩码（如：李白 → 李*）
     * - 三字及以上：保留姓和最后一个字，中间掩码（如：李世民 → 李*民）
     * @param name 姓名
     * @return 脱敏后的姓名
     */
    public static String maskName(String name) {
        if (StringUtils.isBlank(name)) {
            return name;
        }
        int nameLen = name.length();
        if (nameLen == 1) {
            return name;
        } else if (nameLen == 2) {
            return name.substring(0, 1) + "*";
        } else {
            // 姓 + 中间* + 最后一个字
            return name.substring(0, 1) +
                "*".repeat(nameLen - 2) +
                name.substring(nameLen - 1);
        }
    }

    /**
     * 身份证号脱敏：保留前3位，后4位，中间掩码（如：110***********1234）
     * @param idCard 身份证号（支持15位/18位）
     * @return 脱敏后的身份证号
     */
    public static String maskIdCard(String idCard) {
        if (StringUtils.isBlank(idCard) || (idCard.length() != 15 && idCard.length() != 18)) {
            return idCard;
        }
        return maskGeneral(idCard, 3, 4);
    }

    /**
     * 银行卡号脱敏：保留前6位，后4位，中间掩码（如：622260**********1234）
     * @param bankCard 银行卡号（常规16/19位）
     * @return 脱敏后的银行卡号
     */
    public static String maskBankCard(String bankCard) {
        if (StringUtils.isBlank(bankCard) || bankCard.length() < 10) {
            return bankCard;
        }
        return maskGeneral(bankCard, 6, 4);
    }

    // ===================== 测试用例（可直接运行验证） =====================
    public static void main(String[] args) {
        // 测试手机号脱敏
        System.out.println("手机号脱敏：" + maskMobile("13812345678")); // 输出：138****5678
        // 测试邮箱脱敏
        System.out.println("邮箱脱敏：" + maskEmail("1234567890@qq.com")); // 输出：123****@qq.com
        // 测试姓名脱敏
        System.out.println("单字名：" + maskName("李")); // 输出：李
        System.out.println("两字名：" + maskName("李白")); // 输出：李*
        System.out.println("三字名：" + maskName("李世民")); // 输出：李*民
        // 测试身份证脱敏
        System.out.println("身份证脱敏：" + maskIdCard("110101199001011234")); // 输出：110***********1234
        // 测试银行卡脱敏
        System.out.println("银行卡脱敏：" + maskBankCard("6222600000000001234")); // 输出：622260**********1234
        // 测试空值/异常值
        System.out.println("空手机号：" + maskMobile(null)); // 输出：null
        System.out.println("无效邮箱：" + maskEmail("123456")); // 输出：123456
    }
}
