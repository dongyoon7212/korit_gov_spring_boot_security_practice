package com.korit.security_practice.mapper;

import com.korit.security_practice.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {
    Optional<User> getUserByEmail(String email);
    Optional<User> getUserByUserId(Integer userId);
    Optional<User> getUserByUsername(String username);
    void addUser(User user);
    int modifyUsername(User user);
    int modifyPassword(User user);
}
