package com.korit.security_practice.repository;

import com.korit.security_practice.entity.Verify;
import com.korit.security_practice.mapper.VerifyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class VerifyRepository {

    @Autowired
    private VerifyMapper verifyMapper;

    public void addVerifyCode(Verify verify) {
        verifyMapper.addVerifyCode(verify);
    }

    public Optional<Verify> getVerifyCode(Integer userId) {
        return verifyMapper.getVerifyCode(userId);
    }

    public void deleteVerifyCode(Integer userId) {
        verifyMapper.deleteVerifyCode(userId);
    }
}
