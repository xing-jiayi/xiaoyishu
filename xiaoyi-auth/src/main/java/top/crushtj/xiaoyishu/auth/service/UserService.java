package top.crushtj.xiaoyishu.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.crushtj.framework.common.response.Response;
import top.crushtj.xiaoyishu.auth.domain.entity.UserEntity;
import top.crushtj.xiaoyishu.auth.model.vo.user.UserLoginReqVO;

/**
 * @author ayi
 * @version V1.0
 * @title UserService
 * @date 2026-01-18 20:09:57
 * @description 用户表(t_user)表服务接口
 */

public interface UserService extends IService<UserEntity> {

    /**
     * 登录或注册
     *
     * @param userLoginReqVO 登录或注册参数
     * @return 登录结果
     */
    Response<String> loginOrRegister(UserLoginReqVO userLoginReqVO);
}

