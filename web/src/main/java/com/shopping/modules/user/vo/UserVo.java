package com.shopping.modules.user.vo;

import lombok.Data;

@Data
public class UserVo {
    private Long id;
    private String userAccount;
    private String userPassword;
    private String userRole;
}
