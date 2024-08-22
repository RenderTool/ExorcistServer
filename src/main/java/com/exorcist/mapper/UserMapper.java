package com.exorcist.mapper;

import com.exorcist.pojo.User;
import java.util.List;

public interface UserMapper {

    public List<User> selectAll();

    public User selectById(int id);

    public User selectByUsername(String username);

    public User selectByEmail(String email);

    public void insertUser(User user);

    public void updateUser(User user);

    public  void deleteUser(int id);
}
