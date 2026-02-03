package top.crushtj.xiaoyi.auth.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Preconditions;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import top.crushtj.framework.common.enums.DeleteEnum;
import top.crushtj.framework.common.enums.StatusEnum;
import top.crushtj.framework.common.exception.BizException;
import top.crushtj.framework.common.response.Response;
import top.crushtj.framework.common.utils.IdGenerator;
import top.crushtj.framework.common.utils.JsonUtils;
import top.crushtj.framework.common.utils.MaskUtils;
import top.crushtj.xiaoyi.auth.constant.RedisKeyConstants;
import top.crushtj.xiaoyi.auth.domain.entity.RoleEntity;
import top.crushtj.xiaoyi.auth.domain.entity.UserEntity;
import top.crushtj.xiaoyi.auth.domain.entity.UserRoleRelEntity;
import top.crushtj.xiaoyi.auth.domain.mappers.RoleMapper;
import top.crushtj.xiaoyi.auth.domain.mappers.UserMapper;
import top.crushtj.xiaoyi.auth.domain.mappers.UserRoleRelMapper;
import top.crushtj.xiaoyi.auth.enums.LoginTypeEnum;
import top.crushtj.xiaoyi.auth.enums.ResponseCodeEnum;
import top.crushtj.xiaoyi.auth.model.vo.user.UserLoginReqVO;
import top.crushtj.xiaoyi.auth.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static top.crushtj.xiaoyi.auth.constant.RedisKeyConstants.XIAOYI_ID_GENERATOR_KEY;
import static top.crushtj.xiaoyi.auth.constant.RoleConstants.COMMON_USER_ROLE_ID;
import static top.crushtj.xiaoyi.auth.constant.XiaoyiAuthConstants.NICK_NAME_PREFIX;
import static top.crushtj.xiaoyi.auth.constant.XiaoyiAuthConstants.XIAOYI_ID_INITIAL_VALUE;

/**
 * @author ayi
 * @version V1.0
 * @title UserServiceImpl
 * @date 2026-01-18 20:09:58
 * @description 用户表(t_user)表服务实现类
 */

@Slf4j
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private UserRoleRelMapper userRoleRelMapper;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private TransactionTemplate transactionTemplate;

    @Override
    public Response<String> loginOrRegister(UserLoginReqVO userLoginReqVO) {
        String phone = userLoginReqVO.getPhone();
        Integer type = userLoginReqVO.getType();
        Long userId = null;
        LoginTypeEnum loginTypeEnum = LoginTypeEnum.valueOf(type);
        switch (loginTypeEnum) {
            case PASSWORD -> {
                String password = userLoginReqVO.getPassword();
                // 密码登录

            }
            case VERIFICATION_CODE -> {
                String verificationCode = userLoginReqVO.getCode();
                // 验证码登录
                // 校验入参验证码是否为空
                Preconditions.checkArgument(StringUtils.isNotBlank(verificationCode), "验证码不能为空");

                // 构建验证码 Redis Key
                String key = RedisKeyConstants.buildVerificationCodeKey(phone);
                // 查询存储在 Redis 中该用户的登录验证码
                String sentCode = (String)redisTemplate.opsForValue()
                    .get(key);

                // 判断用户提交的验证码，与 Redis 中的验证码是否一致
                if (!StringUtils.equals(verificationCode, sentCode)) {
                    throw new BizException(ResponseCodeEnum.VERIFICATION_CODE_ERROR);
                }

                // 通过手机号查询记录
                UserEntity userEntity = userMapper.selectByPhone(phone);

                log.info("==> 用户是否注册, phone: {}, userId: {}", MaskUtils.maskMobile(phone),
                    userEntity == null ? "未注册" : userEntity.getXiaoyishuId());

                // 判断是否注册
                if (Objects.isNull(userEntity)) {
                    // 若此用户还没有注册，系统自动注册该用户
                    // todo 自动注册用户逻辑
                    userId = registerUser(phone);

                } else {
                    // 已注册，则获取其用户 ID
                    userId = userEntity.getId();
                    this.loadUserRole(userId);
                }
            }
        }
        StpUtil.login(userId);

        // 获取 Token 令牌
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();

        // 返回 Token 令牌
        return Response.success(tokenInfo.tokenValue);
    }

    /**
     * 用户注册
     *
     * @param phone 手机号
     * @return 用户ID
     */
    private Long registerUser(String phone) {
        return transactionTemplate.execute(status -> {
            try {
                long userId = IdGenerator.getInstance()
                    .nextId();
                // 小壹书用户ID，非数据库主键ID
                Long xiaoyishuId = redisTemplate.opsForValue()
                    .increment(XIAOYI_ID_GENERATOR_KEY);
                UserEntity userEntity = UserEntity.builder()
                    .id(userId)
                    .phone(phone)
                    .xiaoyishuId(String.valueOf(xiaoyishuId))
                    .nickname(NICK_NAME_PREFIX + xiaoyishuId) // 自动注册的用户昵称默认为"咿呀_" + 用户ID
                    .status(StatusEnum.ENABLED.getValue())
                    .createTime(LocalDateTime.now())
                    .updateTime(LocalDateTime.now())
                    .isDeleted(DeleteEnum.NO.getValue())
                    .build();
                save(userEntity);

                UserRoleRelEntity userRoleRel = UserRoleRelEntity.builder()
                    .id(IdGenerator.getInstance()
                        .nextId())
                    .userId(userId)
                    .roleId(COMMON_USER_ROLE_ID) // 自动注册的用户角色为普通用户
                    .createTime(LocalDateTime.now())
                    .updateTime(LocalDateTime.now())
                    .isDeleted(DeleteEnum.NO.getValue())
                    .build();
                int i = 1 / 0;
                userRoleRelMapper.insert(userRoleRel);

                // 将用户角色信息存储到 Redis 中
                RoleEntity role = roleMapper.selectById(COMMON_USER_ROLE_ID);
                List<String> roles = new ArrayList<>(1);
                roles.add(role.getRoleKey());
                String userRolesKey = RedisKeyConstants.buildUserRolesKey(userId);
                redisTemplate.opsForValue()
                    .set(userRolesKey, JsonUtils.toJsonString(roles));
                return userId;
            } catch (Exception e) {
                // 回滚事务
                log.error("==> 用户注册异常，开始回滚事务: ", e);
                status.setRollbackOnly();
                // 回滚 redis 自增ID
                Long decrement = Long.valueOf(Objects.requireNonNull(redisTemplate.opsForValue()
                        .get(XIAOYI_ID_GENERATOR_KEY))
                    .toString()
                    .trim());
                if (decrement.compareTo(XIAOYI_ID_INITIAL_VALUE) > 0) {
                    log.error("==> 用户注册异常，回滚redis自增ID: {}", decrement);
                    redisTemplate.opsForValue()
                        .decrement(XIAOYI_ID_GENERATOR_KEY);
                }
            }
            return null;
        });
    }

    /**
     * 加载用户角色信息到缓存
     *
     * @param userId 用户ID
     */
    private void loadUserRole(Long userId) {
        String userRolesKey = RedisKeyConstants.buildUserRolesKey(userId);
        Boolean hasKey = redisTemplate.hasKey(userRolesKey);
        if (!hasKey) {
            List<String> roles = roleMapper.selectRoleKeyByUserId(userId);
            redisTemplate.opsForValue()
                .set(userRolesKey, JsonUtils.toJsonString(roles));
        }
    }
}

