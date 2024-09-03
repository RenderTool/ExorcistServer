package com.exorcist.service;

import com.exorcist.dto.user.LoginDTO;
import com.exorcist.dto.user.RegisterDTO;
import com.exorcist.dto.user.PasswordDTO;
import com.exorcist.exception.ResultInfo;

public interface UserService
{

    //注册
    ResultInfo registerUser(RegisterDTO registerDTO);

    //登录
    ResultInfo loginUser(LoginDTO loginDTO);

    //更新密码
    ResultInfo updateUserPassword(PasswordDTO updateUserPasswordDTO,String token);

    ResultInfo logoutUser();
}
