package top.crushtj.xiaoyishu.auth;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.crushtj.xiaoyishu.auth.domain.entity.UserEntity;
import top.crushtj.xiaoyishu.auth.domain.mappers.UserMapper;

import java.time.LocalDateTime;

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
        UserEntity user = UserEntity.builder()
                                    .username("刑加一")
                                    .createTime(LocalDateTime.now())
                                    .updateTime(LocalDateTime.now())
                                    .build();

        userMapper.insert(user);
    }

}
