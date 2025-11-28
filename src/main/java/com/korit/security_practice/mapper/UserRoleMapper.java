package com.korit.security_practice.mapper;

import com.korit.security_practice.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserRoleMapper {
    void addUserRole(UserRole userRole);
    List<UserRole> getUserRoleList(Integer userId);
    int updateUserRole(UserRole userRole);
}
