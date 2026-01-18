package top.crushtj.xiaoyishu.auth.controller;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.crushtj.framework.biz.operationlog.aspect.ApiOperationLog;
import top.crushtj.framework.common.response.Response;
import top.crushtj.xiaoyishu.auth.model.vo.user.UserLoginReqVO;
import top.crushtj.xiaoyishu.auth.service.UserService;

   /**
 * @author ayi
 * @version V1.0
 * @title UserController
 * @date 2026-01-18 20:40:21
 * @description 用户表(User)表控制层
*/

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    @ApiOperationLog(description = "用户登录或注册")
    public Response<String> loginOrRegister(@RequestBody @Validated UserLoginReqVO userLoginReqVO) {
        return userService.loginOrRegister(userLoginReqVO);
    }
}

