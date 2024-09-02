package com.exorcist.service.impl;

import com.exorcist.dto.user.LoginDTO;
import com.exorcist.dto.user.RegisterDTO;
import com.exorcist.dto.user.PasswordDTO;
import com.exorcist.exception.ResultInfo;
import com.exorcist.mapper.UserMapper;
import com.exorcist.pojo.UserPojo;
import com.exorcist.service.UserService;
import com.exorcist.util.EncryptionUtil;
import com.exorcist.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private EncryptionUtil encryptionUtil;

    @Override
    public ResultInfo registerUser(RegisterDTO registerDTO) {
        // 判断用户名或邮箱是否已经存在
        if (userMapper.selectByEmail(registerDTO.getEmail()) != null) {
            return ResultInfo.error("邮箱已被注册");
        }
        if (userMapper.selectByMobile(registerDTO.getMobile()) != null) {
            return ResultInfo.error("手机号已被使用");
        }

        // 创建用户对象并设置字段
        UserPojo userPojo = new UserPojo();
        userPojo.setName(registerDTO.getUsername());
        userPojo.setEmail(registerDTO.getEmail());
        userPojo.setMobile(registerDTO.getMobile());

        // 加密密码, 具体加密方法见 encryptionUtil
        String salt = encryptionUtil.generateSalt();
        userPojo.setSalt(salt);
        userPojo.setPassword(encryptionUtil.encryptPassword(registerDTO.getPassword(), salt));
        userPojo.setStatus(true);
        userPojo.setCreate_time(LocalDateTime.now().toString());

        // 插入用户
        userMapper.insertUser(userPojo);

        return ResultInfo.success();
    }

    @Override
    public ResultInfo loginUser(LoginDTO loginDTO) {
        UserPojo dbUserPojo = null;
        if (loginDTO.getEmail() != null) {
            dbUserPojo = userMapper.selectByEmail(loginDTO.getEmail());
        } else if (loginDTO.getMobile() != null) {
            dbUserPojo = userMapper.selectByMobile(loginDTO.getMobile());
        }

        if (dbUserPojo == null) {
            return ResultInfo.error("账号未注册");
        }

        if (encryptionUtil.verifyPassword(loginDTO.getPassword(), dbUserPojo.getPassword(), dbUserPojo.getSalt())) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("ID", dbUserPojo.getId());
            String token = JwtUtil.createToken(claims);
            return ResultInfo.success(token);
        } else {
            return ResultInfo.error("账号或密码错误，请重新输入");
        }
    }

    @Override
    public ResultInfo updateUserPassword(PasswordDTO updateUserPasswordDTO) {
        // 比对密码，检查是否重复
        if (Objects.equals(updateUserPasswordDTO.getNewPassword(), updateUserPasswordDTO.getOldPassword())) {
            return ResultInfo.error("参数错误");
        }

        UserPojo dbUserPojo = userMapper.selectByMobile(updateUserPasswordDTO.getMobile());

        if (dbUserPojo != null && encryptionUtil.verifyPassword(updateUserPasswordDTO.getOldPassword(), dbUserPojo.getPassword(), dbUserPojo.getSalt())) {
            // 加密新密码
            String salt = encryptionUtil.generateSalt();
            dbUserPojo.setSalt(salt);
            dbUserPojo.setPassword(encryptionUtil.encryptPassword(updateUserPasswordDTO.getNewPassword(), salt));
            userMapper.updateUser(dbUserPojo);
            return ResultInfo.success("密码更新成功");
        }

        return ResultInfo.error("旧密码错误，请重新输入");
    }
}
