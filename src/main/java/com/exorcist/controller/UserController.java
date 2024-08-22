package com.exorcist.controller;

import com.exorcist.service.UserService;
import com.exorcist.util.EncryptionUtil;
import com.exorcist.pojo.User;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EncryptionUtil encryptionUtil;

    @Operation(summary = "用户注册", description = "注册新用户")
    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        String salt = encryptionUtil.generateSalt();
        user.setSalt(salt);
        user.setPassword(encryptionUtil.encryptPassword(user.getPassword(), salt));
        userService.insertUser(user);
        return "用户注册成功";
    }

    @Operation(summary = "找回密码", description = "通过邮箱找回密码")
    @PostMapping("/forgotPassword")
    public String forgotPassword(@RequestParam String email) {
        User user = userService.selectByEmail(email);
        if (user != null) {
            String tempPassword = encryptionUtil.generateTemporaryPassword();
            user.setPassword(encryptionUtil.encryptPassword(tempPassword, user.getSalt()));
            userService.updateUser(user);
            // 发送临时密码到用户邮箱
            return "临时密码已发送到您的邮箱";
        }
        return "邮箱不存在";
    }

    @Operation(summary = "获取用户列表", description = "获取用户的详细信息")
    @GetMapping("/getAllUserInfo")
    public List<User> getAllUserInfo() {
        return userService.selectAll();
    }

    @Operation(summary = "根据ID获取用户信息", description = "通过用户ID获取用户的详细信息")
    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.selectById(id);
    }

//    @Operation(summary = "用户登录", description = "通过用户名和密码登录")
//    @PostMapping("/login")
//    public String loginUser(@RequestBody User user) {
//        User dbUser = userMapper.selectByUsername(user.getName());
//        if (dbUser != null && encryptionUtil.verifyPassword(user.getPassword(), dbUser.getPassword(), dbUser.getSalt())) {
//            return jwtTokenUtil.generateToken(dbUser);
//        }
//        return "用户名或密码错误";
//    }

    @Operation(summary = "更新用户信息", description = "更新用户的详细信息")
    @PutMapping("/update")
    public String updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return "用户信息更新成功";
    }

    @Operation(summary = "删除用户", description = "通过用户ID删除用户")
    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return "用户删除成功";
    }
}
