package com.promoit.otp.dao;

import com.promoit.otp.model.User;
import java.util.List;

public interface UserDao {
    void save(User user);
    User findByLogin(String login);
    List<User> findAll();
    void deleteByLogin(String login);
}