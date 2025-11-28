package com.korit.security_practice.controller;

import com.korit.security_practice.dto.SigninReqDto;
import com.korit.security_practice.dto.SignupReqDto;
import com.korit.security_practice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupReqDto signupReqDto) {
        return ResponseEntity.ok(authService.signup(signupReqDto));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody SigninReqDto signinReqDto) {
        return ResponseEntity.ok(authService.signin(signinReqDto));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(authService.getUserByUserId(userId));
    }
}
