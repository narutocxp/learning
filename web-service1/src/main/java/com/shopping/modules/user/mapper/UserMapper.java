package com.shopping.modules.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shopping.modules.user.entity.UserEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends BaseMapper<UserEntity> {
    List<UserEntity> findByAccount(@Param("userAccount") String userAccount);
}