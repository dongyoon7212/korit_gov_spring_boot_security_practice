package com.korit.security_practice.mapper;

import com.korit.security_practice.entity.Verify;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface VerifyMapper {
    void addVerifyCode(Verify verify);
    Optional<Verify> getVerifyCode(Integer userId);
    void deleteVerifyCode(Integer userId);
}
