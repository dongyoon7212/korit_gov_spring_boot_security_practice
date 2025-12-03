package com.korit.security_practice.service;

import com.korit.security_practice.dto.ApiRespDto;
import com.korit.security_practice.dto.SendMailReqDto;
import com.korit.security_practice.dto.VerifyCodeReqDto;
import com.korit.security_practice.entity.User;
import com.korit.security_practice.entity.UserRole;
import com.korit.security_practice.entity.Verify;
import com.korit.security_practice.repository.UserRepository;
import com.korit.security_practice.repository.UserRoleRepository;
import com.korit.security_practice.repository.VerifyRepository;
import com.korit.security_practice.security.model.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class MailService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private VerifyRepository verifyRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    public ApiRespDto<?> sendMail(SendMailReqDto sendMailReqDto, Principal principal) {
        if (!sendMailReqDto.getEmail().equals(principal.getEmail())) {
            return new ApiRespDto<>("failed", "잘못된 접근입니다.", null);
        }
        Optional<User> foundUser = userRepository.getUserByEmail(sendMailReqDto.getEmail());
        if (foundUser.isEmpty()) {
            return new ApiRespDto<>("failed", "이메일 주소를 다시 확인해주세요.", null);
        }

        User user = foundUser.get();

        boolean hasRole = user.getUserRoles().stream()
                .anyMatch(userRole -> userRole.getRole().getRoleId() == 3);

        if (!hasRole) {
            return new ApiRespDto<>("failed", "인증이 필요한 계정이 아닙니다.", null);
        }

        StringBuilder sb = new StringBuilder();
        Random rd = new Random();

        for(int i=0;i<5;i++){
            sb.append(rd.nextInt(10));
        }

        Verify verify = Verify.builder()
                .userId(user.getUserId())
                .verifyCode(sb.toString())
                .build();
        verifyRepository.addVerifyCode(verify);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(sendMailReqDto.getEmail());
        message.setSubject("[이메일 인증] 인증코드");
        message.setText("인증코드입니다. =>>> " + verify.getVerifyCode());
        javaMailSender.send(message);

        return new ApiRespDto<>("success", "인증 코드가 이메일로 전송되었습니다. 이메일을 확인하세요.", null);
    }

    public ApiRespDto<?> verifyCode(VerifyCodeReqDto verifyCodeReqDto, Principal principal) {
        Optional<Verify> foundVerify = verifyRepository.getVerifyCode(principal.getUserId());
        if (foundVerify.isEmpty()) {
            return new ApiRespDto<>("failed", "문제가 발생했습니다. 인증코드를 다시 요청하세요.", null);
        }

        Optional<User> foundUser = userRepository.getUserByUserId(principal.getUserId());
        if (foundUser.isEmpty()) {
            return new ApiRespDto<>("failed", "문제가 발생했습니다.", null);
        }

        if (!verifyCodeReqDto.getVerifyCode().equals(foundVerify.get().getVerifyCode())) {
            return new ApiRespDto<>("failed", "인증코드가 일치하지 않습니다.", null);
        }

        Optional<UserRole> foundUserRole = foundUser.get().getUserRoles().stream()
                .filter(userRole1 -> userRole1.getRole().getRoleId() == 3)
                .findFirst();

        if (foundUserRole.isEmpty()) {
            return new ApiRespDto<>("failed", "문제가 발생했습니다.", null);
        }

        UserRole userRole = foundUserRole.get();
        userRole.setRoleId(2);

        userRoleRepository.updateUserRole(userRole);

        verifyRepository.deleteVerifyCode(foundUser.get().getUserId());

        return new ApiRespDto<>("success", "인증이 완료되었습니다.", null);
    }
}










