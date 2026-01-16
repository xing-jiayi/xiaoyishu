package top.crushtj.xiaoyishu.auth;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.crushtj.xiaoyishu.auth.domain.entity.UserEntity;
import top.crushtj.xiaoyishu.auth.domain.mappers.UserMapper;

import java.time.LocalDateTime;

/**
 *
 * @author ayi
 * @title XiaoyiAuthApplicationTests
 * @description 测试类
 * @date 2025/11/20
 */

@SpringBootTest
@Slf4j
class XiaoyiAuthApplicationTests {

    @Resource
    UserMapper userMapper;

    @Test
    void contextLoads() {
    }

    @Test
    void testInsert() {
        UserEntity user =
            UserEntity.builder().username("刑加一").createTime(LocalDateTime.now()).updateTime(LocalDateTime.now())
                .build();

        userMapper.insert(user);
    }

    @Test
    void queryTest() {
        LambdaQueryWrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserEntity::getUsername, "刑加一");
        queryWrapper.orderByDesc(UserEntity::getCreateTime);
        queryWrapper.last("limit 1");
        UserEntity user = userMapper.selectOne(queryWrapper);
        log.info("查询结果：{}", user);
    }

}
