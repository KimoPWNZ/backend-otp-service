package com.promoit.otp.dao;

import com.promoit.otp.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserDaoInMemoryImpl implements UserDao {
    private final Map<String, User> users = new HashMap<>();

    @Override
    public void save(User user) {
        users.put(user.getLogin(), user);
    }

    @Override
    public User findByLogin(String login) {
        return users.get(login);
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public void deleteByLogin(String login) {
        users.remove(login);
    }
}