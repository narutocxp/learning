package com.shopping.modules.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")
public class UserEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String userAccount;
    private String userPassword;
    private String userRole;
}