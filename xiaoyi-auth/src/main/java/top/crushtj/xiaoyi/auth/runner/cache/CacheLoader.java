package top.crushtj.xiaoyi.auth.runner.cache;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import top.crushtj.xiaoyi.auth.domain.entity.UserEntity;
import top.crushtj.xiaoyi.auth.domain.mappers.UserMapper;

import static top.crushtj.xiaoyi.auth.constant.RedisKeyConstants.XIAOYI_ID_GENERATOR_KEY;
import static top.crushtj.xiaoyi.auth.constant.XiaoyiAuthConstants.XIAOYI_ID_INITIAL_VALUE;

/**
 * @author ayi
 * @version V1.0
 * @title CacheLoader
 * @date 2026/01/20 18:25
 * @description 初始化缓存
 */

@Slf4j
@Component
public class CacheLoader {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private UserMapper userMapper;

    /**
     * 加载用户自增ID缓存
     */
    @PostConstruct
    public void loadUserCache() {
        log.info("==> 加载用户自增ID缓存开始...");
        LambdaQueryWrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(UserEntity::getXiaoyishuId);
        queryWrapper.last("limit 1");
        UserEntity user = userMapper.selectOne(queryWrapper);
        if (user != null) {
            redisTemplate.opsForValue()
                .set(XIAOYI_ID_GENERATOR_KEY, Long.valueOf(user.getXiaoyishuId()));
        } else {
            redisTemplate.opsForValue()
                .set(XIAOYI_ID_GENERATOR_KEY, XIAOYI_ID_INITIAL_VALUE);
        }
        log.info("==> 加载用户自增ID缓存结束...");
    }
}
