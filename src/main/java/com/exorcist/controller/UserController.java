package com.exorcist.controller;

import com.exorcist.dto.user.PasswordDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

import com.exorcist.dto.user.LoginDTO;
import com.exorcist.dto.user.RegisterDTO;
import com.exorcist.exception.ResultInfo;
import com.exorcist.service.UserService;

@Tag(name="账户管理模块")
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "用户注册", description = "注册新用户")
    @PostMapping("/register")
    public ResultInfo registerUser(@RequestBody @Valid RegisterDTO registerDTO) {
        return userService.registerUser(registerDTO);
    }

    @Operation(summary = "用户登录", description = "通过邮箱或手机号登录")
    @PostMapping("/login")
    public ResultInfo loginUser(@RequestBody @Valid LoginDTO loginDTO) {
        return userService.loginUser(loginDTO);
    }

//    @Operation(summary = "用户登出", description = "登出功能")
//    @PostMapping(value = "/logout")


//    @Operation(summary = "根据ID获取用户信息", description = "通过用户ID获取用户的详细信息")
//    @GetMapping("/userinfo")
//    public ResultInfo getUserInfo() {
//        return ResultInfo.success();
//    }

    @Operation(summary = "更新用户密码", description = "通过手机号更新用户密码")
    @PutMapping("/update")
    public ResultInfo updateUserPassword(@RequestBody @Valid PasswordDTO updateUserPasswordDTO,
                                         @RequestHeader(value = "Authorization") String token) {
        return userService.updateUserPassword(updateUserPasswordDTO,token);
    }


}
