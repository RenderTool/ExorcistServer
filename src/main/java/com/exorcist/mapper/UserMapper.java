package com.exorcist.mapper;

import com.exorcist.pojo.UserPojo;

import java.util.List;

public interface UserMapper {

    public List<UserPojo> selectAll();

    public UserPojo selectById(Long id);

    public UserPojo selectByUsername(String username);

    public UserPojo selectByEmail(String email);

    public UserPojo selectByMobile(String mobile);

    public void insertUser(UserPojo userPojo);

    public void updateUser(UserPojo userPojo);

    public  void deleteUser(int id);
}
