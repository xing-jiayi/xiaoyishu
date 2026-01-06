package top.crushtj.xiaoyishu.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.crushtj.framework.biz.operationlog.aspect.ApiOperationLog;
import top.crushtj.framework.common.response.Response;
import top.crushtj.xiaoyishu.auth.vo.User;

import java.time.LocalDateTime;

/**
 *
 * @Title: TestController
 * @Description: 测试控制器
 * @Author: ayi
 * @Date: 2025/11/21
 */

@RestController
public class TestController {

    @GetMapping("/test")
    @ApiOperationLog(description = "测试接口")
    public Response<User> testController() {
        return Response.success(User.builder()
            .id(1L)
            .name("ayi")
            .age(18)
            .createTime(LocalDateTime.now())
            .build());
    }

    @PostMapping("/test2")
    @ApiOperationLog(description = "测试接口2")
    public Response<User> test2(@RequestBody User user) {
        return Response.success(user);
    }

}
