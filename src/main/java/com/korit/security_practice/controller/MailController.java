package com.korit.security_practice.controller;

import com.korit.security_practice.dto.SendMailReqDto;
import com.korit.security_practice.dto.VerifyCodeReqDto;
import com.korit.security_practice.security.model.Principal;
import com.korit.security_practice.service.MailService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private MailService mailService;

    //메일 보내는거
    @PostMapping("/send")
    public ResponseEntity<?> sendMail(@RequestBody SendMailReqDto sendMailReqDto, @AuthenticationPrincipal Principal principal) {
        return ResponseEntity.ok(mailService.sendMail(sendMailReqDto, principal));
    }

    //인증코드 확인하는거
    @PostMapping("/verify")
    public ResponseEntity<?> verifyCode(@RequestBody VerifyCodeReqDto verifyCodeReqDto, @AuthenticationPrincipal Principal principal) {
        return ResponseEntity.ok(mailService.verifyCode(verifyCodeReqDto, principal));
    }
}











