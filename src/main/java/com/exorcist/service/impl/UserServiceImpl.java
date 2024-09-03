package com.exorcist.service.impl;

import com.exorcist.dto.user.LoginDTO;
import com.exorcist.dto.user.RegisterDTO;
import com.exorcist.dto.user.PasswordDTO;
import com.exorcist.exception.ResultInfo;
import com.exorcist.mapper.UserMapper;
import com.exorcist.pojo.UserPojo;
import com.exorcist.service.UserService;
import com.exorcist.util.EncryptionUtil;
import com.exorcist.util.JwtTokenUtil;

import com.exorcist.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private EncryptionUtil encryptionUtil;

    @Autowired
    private RedisTemplate redisTemplate;

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

        // 加密密码
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
        // 检查用户是否提供了邮箱或手机号
        if (loginDTO.getEmail() == null && loginDTO.getMobile() == null) {
            return ResultInfo.error("请输入邮箱或手机号");
        }

        UserPojo dbUserPojo = null;

        // 根据提供的邮箱或手机号查找用户
        if (loginDTO.getEmail() != null) {
            dbUserPojo = userMapper.selectByEmail(loginDTO.getEmail());
        } else if (loginDTO.getMobile() != null) {
            dbUserPojo = userMapper.selectByMobile(loginDTO.getMobile());
        }

        // 如果用户不存在，返回统一的错误信息
        if (dbUserPojo == null) {
            return ResultInfo.error("账号或密码错误，请重新输入");
        }

        // 验证密码
        if (encryptionUtil.verifyPassword(loginDTO.getPassword(), dbUserPojo.getPassword(), dbUserPojo.getSalt())) {

            // 创建token
            Map<String, Object> claims = new HashMap<>();
            claims.put("ID", dbUserPojo.getId());
            String token = JwtTokenUtil.createToken(claims);
            Long time = JwtTokenUtil.getExpirationTimeInSeconds(token);
            System.out.println("用户id"+dbUserPojo.getId() +"token过期时间："+time);

            // 存储到Redis中
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            operations.set(dbUserPojo.getId().toString(), token, time, TimeUnit.SECONDS);

            return ResultInfo.success(token);

        } else {
            return ResultInfo.error("账号或密码错误，请重新输入");
        }
    }



    @Override
    public ResultInfo updateUserPassword(PasswordDTO updateUserPasswordDTO, String token) {
        // 比对密码，检查是否重复
        if (Objects.equals(updateUserPasswordDTO.getNewPassword(), updateUserPasswordDTO.getOldPassword())) {
            return ResultInfo.error("新旧密码重复");
        }
        UserPojo dbUserPojo = userMapper.selectByMobile(updateUserPasswordDTO.getMobile());

        if(dbUserPojo == null || dbUserPojo.getId() == null)
        {
            return ResultInfo.error("状态异常");
        }
        if (encryptionUtil.verifyPassword(updateUserPasswordDTO.getOldPassword(), dbUserPojo.getPassword(), dbUserPojo.getSalt())) {

            // 删除Redis中的token
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            operations.getOperations().delete(dbUserPojo.getId().toString());

            // 加密新密码
            String salt = encryptionUtil.generateSalt();
            dbUserPojo.setSalt(salt);
            dbUserPojo.setPassword(encryptionUtil.encryptPassword(updateUserPasswordDTO.getNewPassword(), salt));
            userMapper.updateUser(dbUserPojo);

            return ResultInfo.success("密码更新成功");
        }

        return ResultInfo.error("旧密码错误，请重新输入");
    }

    @Override
    public ResultInfo logoutUser() {

        Map<String, Object> getThreadLocal =  ThreadLocalUtil.getThreadLocal();
        String userID = getThreadLocal.get("ID").toString();

        // 删除Redis中的token
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        operations.getOperations().delete(userID);

        return ResultInfo.success("登出成功");
    }
}
