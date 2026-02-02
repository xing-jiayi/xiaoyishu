package top.crushtj.xiaoyi.auth.domain.mappers;

import org.apache.ibatis.annotations.Param;
import top.crushtj.xiaoyi.auth.domain.entity.UserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author ayi
 * @version V1.0
 * @title UserMapper
 * @date 2026-01-19 19:49:51
 * @description 用户表(t_user)表数据库访问层
 */
public interface UserMapper extends BaseMapper<UserEntity> {

    /**
     * 根据手机号查询用户
      * @param phone 手机号
      * @return 用户信息
     */
    UserEntity selectByPhone(@Param("phone") String phone);
}

