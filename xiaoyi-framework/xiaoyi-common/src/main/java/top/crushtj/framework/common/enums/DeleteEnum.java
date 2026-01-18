package top.crushtj.framework.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ayi
 * @version V1.0
 * @title DeleteEnum
 * @date 2026/01/18 21:15
 * @description 删除标记
 */

@Getter
@AllArgsConstructor
public enum DeleteEnum {
    YES(true),
    NO(false);

    private final Boolean value;
}
