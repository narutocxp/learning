package com.shopping.modules.user.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shopping.modules.user.entity.UserEntity;
import com.shopping.modules.user.mapper.UserMapper;
import com.shopping.modules.user.vo.UserVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserService extends ServiceImpl<UserMapper, UserEntity> {
    public Boolean save(UserVo userVo) {
        UserEntity user = new UserEntity();
        BeanUtils.copyProperties(userVo, user);
        return save(user);
    }

    public UserVo findByAccount(@Param("userAccount") String userAccount) {
        List<UserEntity> dataList = baseMapper.findByAccount(userAccount);
        if (CollectionUtils.isEmpty(dataList)) {
            return null;
        }

        UserEntity userEntity = dataList.get(0);
        if (userEntity == null) {
            return null;
        }
        UserVo result = new UserVo();
        BeanUtils.copyProperties(userEntity, result);
        return result;
    }

    public List<UserVo> findList(String userAccount) {
        List<UserEntity> dataList = baseMapper.findByAccount(userAccount);
        if (CollectionUtils.isEmpty(dataList)) {
            return Collections.emptyList();
        }
        List<UserVo> resultList = new ArrayList<>();
        for (UserEntity userEntity : dataList) {
            UserVo result = new UserVo();
            BeanUtils.copyProperties(userEntity, result);
            resultList.add(result);
        }
        return resultList;
    }
}