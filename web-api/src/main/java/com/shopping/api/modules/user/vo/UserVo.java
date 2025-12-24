package com.shopping.api.modules.user.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserVo implements Serializable {
    private Long id;
    private String userAccount;
    private String userPassword;
    private String userRole;
}
