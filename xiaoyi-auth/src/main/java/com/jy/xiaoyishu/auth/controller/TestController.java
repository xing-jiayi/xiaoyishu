package com.jy.xiaoyishu.auth.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jy.framework.biz.operationlog.aspect.ApiOperationLog;
import com.jy.framework.common.response.Response;
import com.jy.xiaoyishu.auth.model.vo.User;

import cn.dev33.satoken.stp.StpUtil;

import java.time.LocalDateTime;

/**
 *
 * @author ayi
 * @title TestController
 * @description 测试控制器
 * @date 2025/11/21
 */

@RestController
public class TestController {

    @GetMapping("/test")
    @ApiOperationLog(description = "测试接口")
    public Response<User> testController() {
        return Response.success(User.builder().id(1L).name("ayi").age(18).createTime(LocalDateTime.now()).build());
    }

    @PostMapping("/test2")
    @ApiOperationLog(description = "测试接口2")
    public Response<User> test2(@RequestBody @Validated User user) {
        // int i = 1 / 0;
        return Response.success(user);
    }

    @RequestMapping("/user/doLogin")
    public String doLogin(String username, String password) {
        // 此处仅作模拟示例，真实项目需要从数据库中查询数据进行比对
        if ("ayi".equals(username) && "12345678".equals(password)) {
            StpUtil.login(10001);
            return "登录成功";
        }
        return "登录失败";
    }

    // 查询登录状态，浏览器访问： http://localhost:8080/user/isLogin
    @RequestMapping("/user/isLogin")
    public String isLogin() {
        return "当前会话是否登录：" + StpUtil.isLogin();
    }

}
