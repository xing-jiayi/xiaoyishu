package top.crushtj.xiaoyi.gateway.auth;

import cn.dev33.satoken.stp.StpInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ayi
 * @version V1.0
 * @title StpInterfaceImpl
 * @date 2026/2/2 17:14
 * @description 自定义权限验证接口
 */

@Slf4j
@Component
public class StpInterfaceImpl implements StpInterface {
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        log.info("## 获取用户权限列表, loginId: {}", loginId);
        return List.of();
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        log.info("## 获取用户角色列表, loginId: {}", loginId);
        return List.of();
    }
}
