package com.promoit.otp.service;

import com.promoit.otp.dao.UserDao;
import com.promoit.otp.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean register(User user) {
        if (userDao.findByLogin(user.getLogin()) != null) {
            return false;
        }
        // Только один админ в системе
        if ("ADMIN".equals(user.getRole()) && userDao.findAll().stream().anyMatch(u -> "ADMIN".equals(u.getRole()))) {
            return false;
        }
        userDao.save(user);
        return true;
    }

    public User login(String login, String passwordHash) {
        User user = userDao.findByLogin(login);
        if (user != null && user.getPasswordHash().equals(passwordHash)) {
            return user;
        }
        return null;
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    public void deleteUser(String login) {
        userDao.deleteByLogin(login);
    }
}