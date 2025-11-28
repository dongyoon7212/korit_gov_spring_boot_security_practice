package com.korit.security_practice.repository;

import com.korit.security_practice.entity.UserRole;
import com.korit.security_practice.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRoleRepository {

    @Autowired
    private UserRoleMapper userRoleMapper;

    public void addUserRole(UserRole userRole) {
        userRoleMapper.addUserRole(userRole);
    }

    public List<UserRole> getUserRoleList(Integer userId) {
        return userRoleMapper.getUserRoleList(userId);
    }

    public int updateUserRole(UserRole userRole) {
        return userRoleMapper.updateUserRole(userRole);
    }
}
