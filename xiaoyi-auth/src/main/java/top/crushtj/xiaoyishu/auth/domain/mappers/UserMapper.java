package top.crushtj.xiaoyishu.auth.domain.mappers;

import org.apache.ibatis.annotations.Param;
import top.crushtj.xiaoyishu.auth.domain.entity.UserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author ayi
 * @version V1.0
 * @title User
 * @date 2026/01/18 19:33:00
 * @description 用户表(t_user)表数据库访问层
 */
public interface UserMapper extends BaseMapper<UserEntity> {

    UserEntity selectByPhone(@Param("phone") String phone);
}

