package top.crushtj.xiaoyishu.auth.vo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 *
 * @Title: User
 * @Description: 测试用户类
 * @Author: ayi
 * @Date: 2025/11/26
 */

@Data
@Builder
public class User implements Serializable {
    private Long id;
    private String name;
    private Integer age;
    private LocalDateTime createTime;

}
