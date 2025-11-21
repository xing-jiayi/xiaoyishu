package top.crushtj.xiaoyishu.auth.controller;

import org.springframework.web.bind.annotation.RestController;
import top.crushtj.framework.biz.operationlog.aspect.ApiOperationLog;
import top.crushtj.framework.common.response.Response;
import org.springframework.web.bind.annotation.GetMapping;

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
    public Response<String> testController() {
        return Response.success("Hello World!");
    }

}
