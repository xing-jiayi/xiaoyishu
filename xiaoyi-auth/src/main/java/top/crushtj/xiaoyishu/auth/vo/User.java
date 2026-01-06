package top.crushtj.xiaoyishu.auth.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @Title: User
 * @Description: 测试用户类
 * @Author: ayi
 * @Date: 2025/11/26
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private Long id;
    private String name;
    private Integer age;
    private LocalDateTime createTime;

}
