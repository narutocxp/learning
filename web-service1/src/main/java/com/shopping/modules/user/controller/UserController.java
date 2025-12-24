package com.shopping.modules.user.controller;

import com.shopping.api.modules.user.vo.UserVo;
import com.shopping.core.ResultResponse;
import com.shopping.modules.user.entity.UserEntity;
import com.shopping.modules.user.service.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = {"http://localhost:3000"}, allowCredentials = "true")
public class UserController {
    private static final String LOGIN_USER = "LOGIN_USER";

    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping("/list")
    public ResultResponse<List<UserEntity>> list() {
        List<UserEntity> users = userServiceImpl.list();
        ResultResponse<List<UserEntity>> response = new ResultResponse<>();
        response.setData(users);
        return response;
    }

    @GetMapping("/{id}")
    public ResultResponse<UserEntity> getUserById(@PathVariable Long id) {
        UserEntity user = userServiceImpl.getById(id);
        ResultResponse<UserEntity> response = new ResultResponse<>();
        response.setData(user);
        return response;
    }

    @PostMapping("/register")
    public ResultResponse<Boolean> register(@RequestBody UserVo userVo) {
        return ResultResponse.success(userServiceImpl.save(userVo));
    }

    @PostMapping("/login")
    public ResultResponse<UserVo> login(@RequestBody UserVo userVo, HttpServletRequest request) {
        UserVo byAccount = userServiceImpl.findByAccount(userVo.getUserAccount());
        if (byAccount == null) {
            return ResultResponse.success(null);
        }
        request.getSession().setAttribute(LOGIN_USER, byAccount);
        return ResultResponse.success(byAccount);
    }

    @GetMapping("/search")
    public ResultResponse<List<UserVo>> search(String userAccount) {
        return ResultResponse.success(userServiceImpl.findList(userAccount));
    }

    @GetMapping("/current")
    public ResultResponse<UserVo> current(HttpServletRequest request) {
        Object attribute = request.getSession().getAttribute(LOGIN_USER);
        if (attribute == null) {
            ResultResponse<UserVo> result = ResultResponse.success(null);
            result.setCode("40100");
            result.setMessage("未登录");
            return result;
        }
        return ResultResponse.success((UserVo) attribute);
    }

    @GetMapping("/delete")
    public ResultResponse<Boolean> deleteUser(@RequestParam("id") Long id) {
        ResultResponse<Boolean> response = new ResultResponse<>();
        response.setData(userServiceImpl.removeById(id));
        return response;
    }
}