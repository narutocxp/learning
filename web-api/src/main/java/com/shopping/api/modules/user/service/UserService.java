package com.shopping.api.modules.user.service;

import com.shopping.api.modules.user.vo.UserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserService {
    Boolean save(UserVo userVo);

    UserVo findByAccount(@Param("userAccount") String userAccount);

    List<UserVo> findList(String userAccount);
}
