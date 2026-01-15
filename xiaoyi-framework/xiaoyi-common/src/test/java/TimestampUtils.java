
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * 时间戳工具类 - 用于获取指定时间的时间戳（毫秒级）
 * 基于Java 8+ java.time包实现，线程安全、支持时区、异常友好
 */
public class TimestampUtils {

    // ====================== 常量定义 ======================
    /** 默认时间格式：yyyy-MM-dd HH:mm:ss */
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    /** 系统默认时区（可根据需求修改，如ZoneId.of("UTC")、ZoneId.of("Asia/Shanghai")） */
    public static final ZoneId DEFAULT_ZONE_ID = ZoneId.systemDefault();

    // ====================== 方法1：通过年月日时分秒获取时间戳 ======================
    /**
     * 获取指定年月日时分秒的时间戳（毫秒级），使用系统默认时区
     * @param year 年（如2024）
     * @param month 月（1-12）
     * @param day 日（1-31）
     * @param hour 时（0-23）
     * @param minute 分（0-59）
     * @param second 秒（0-59）
     * @return 对应时间的毫秒级时间戳
     * @throws IllegalArgumentException 时间参数不合法时抛出
     */
    public static long getTimestamp(int year, int month, int day, int hour, int minute, int second) {
        return getTimestamp(year, month, day, hour, minute, second, DEFAULT_ZONE_ID);
    }

    /**
     * 获取指定年月日时分秒的时间戳（毫秒级），支持指定时区
     * @param year 年（如2024）
     * @param month 月（1-12）
     * @param day 日（1-31）
     * @param hour 时（0-23）
     * @param minute 分（0-59）
     * @param second 秒（0-59）
     * @param zoneId 时区（如ZoneId.of("Asia/Shanghai")）
     * @return 对应时间的毫秒级时间戳
     * @throws IllegalArgumentException 时间参数不合法时抛出
     */
    public static long getTimestamp(int year, int month, int day, int hour, int minute, int second, ZoneId zoneId) {
        try {
            // 构建指定时间的LocalDateTime
            LocalDateTime localDateTime = LocalDateTime.of(year, month, day, hour, minute, second);
            // 转换为指定时区的ZonedDateTime，再获取时间戳
            ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);
            return zonedDateTime.toInstant().toEpochMilli();
        } catch (Exception e) {
            throw new IllegalArgumentException("时间参数不合法：" + year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second, e);
        }
    }

    // ====================== 方法2：解析时间字符串获取时间戳 ======================
    /**
     * 解析指定格式的时间字符串为时间戳（毫秒级），使用系统默认时区
     * @param timeStr 时间字符串（如"2024-01-01 12:00:00"）
     * @param formatter 时间格式化器（如DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")）
     * @return 对应时间的毫秒级时间戳
     * @throws DateTimeParseException 时间字符串格式不匹配时抛出
     */
    public static long getTimestamp(String timeStr, DateTimeFormatter formatter) {
        return getTimestamp(timeStr, formatter, DEFAULT_ZONE_ID);
    }

    /**
     * 解析默认格式（yyyy-MM-dd HH:mm:ss）的时间字符串为时间戳（毫秒级）
     * @param timeStr 时间字符串（如"2024-01-01 12:00:00"）
     * @return 对应时间的毫秒级时间戳
     * @throws DateTimeParseException 时间字符串格式不匹配时抛出
     */
    public static long getTimestamp(String timeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT);
        return getTimestamp(timeStr, formatter, DEFAULT_ZONE_ID);
    }

    /**
     * 解析指定格式的时间字符串为时间戳（毫秒级），支持指定时区
     * @param timeStr 时间字符串（如"2024-01-01 12:00:00"）
     * @param formatter 时间格式化器
     * @param zoneId 时区
     * @return 对应时间的毫秒级时间戳
     * @throws DateTimeParseException 时间字符串格式不匹配时抛出
     */
    public static long getTimestamp(String timeStr, DateTimeFormatter formatter, ZoneId zoneId) {
        try {
            // 解析时间字符串为LocalDateTime
            LocalDateTime localDateTime = LocalDateTime.parse(timeStr, formatter);
            // 转换为指定时区的ZonedDateTime，再获取时间戳
            ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);
            return zonedDateTime.toInstant().toEpochMilli();
        } catch (DateTimeParseException e) {
            throw new DateTimeParseException("时间字符串格式解析失败：" + timeStr + "，期望格式：" + formatter.toString(),
                    e.getParsedString(), e.getErrorIndex(), e);
        }
    }

    // ====================== 测试用例 ======================
    public static void main(String[] args) {
        // 测试方法1：指定年月日时分秒获取时间戳
        long timestamp1 = TimestampUtils.getTimestamp(2024, 1, 1, 0, 0, 0);
        System.out.println("2024-01-01 00:00:00 的时间戳：" + timestamp1); // 输出：1704067200000

        // 测试方法2：指定时区（UTC）获取时间戳
        long timestamp2 = TimestampUtils.getTimestamp(2024, 1, 1, 0, 0, 0, ZoneId.of("UTC"));
        System.out.println("2024-01-01 00:00:00 (UTC) 的时间戳：" + timestamp2); // 输出：1704038400000

        // 测试方法3：解析默认格式的时间字符串
        long timestamp3 = TimestampUtils.getTimestamp("2026-01-01 00:00:00");
        System.out.println("2026-01-01 00:00:00 的时间戳：" + timestamp3);

        // 测试方法4：解析自定义格式的时间字符串
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        long timestamp4 = TimestampUtils.getTimestamp("2024/06/01 12:30", formatter);
        System.out.println("2024/06/01 12:30 的时间戳：" + timestamp4);
    }
}