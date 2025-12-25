package com.shopping.modules.user.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shopping.api.modules.user.service.UserService;
import com.shopping.api.modules.user.vo.UserVo;
import com.shopping.modules.user.entity.UserEntity;
import com.shopping.modules.user.mapper.UserMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@DubboService
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {
    @Override
    public Boolean save(UserVo userVo) {
        UserEntity user = new UserEntity();
        BeanUtils.copyProperties(userVo, user);
        return save(user);
    }

    @Override
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

    @Override
    public List<UserVo> findList(String userAccount) {
        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
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