package top.crushtj.framework.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ayi
 * @version V1.0
 * @title StatusEnum
 * @date 2026/01/18
 * @description 状态
 */

@Getter
@AllArgsConstructor
public enum StatusEnum {
    ENABLED(1),
    DISABLED(0);

    private final Integer value;
}
