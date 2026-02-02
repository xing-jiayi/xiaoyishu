package top.crushtj.xiaoyi.gateway.auth;

import cn.dev33.satoken.stp.StpInterface;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ayi
 * @version V1.0
 * @title StpInterfaceImpl
 * @date 2026/2/2 17:14
 * @description 自定义权限验证接口
 */

@Component
public class StpInterfaceImpl implements StpInterface {
    @Override
    public List<String> getPermissionList(Object o, String s) {
        return List.of();
    }

    @Override
    public List<String> getRoleList(Object o, String s) {
        return List.of();
    }
}
