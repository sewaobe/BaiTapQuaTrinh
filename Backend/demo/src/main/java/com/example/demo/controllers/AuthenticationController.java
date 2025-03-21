package com.example.demo.controllers;

import com.example.demo.DTO.LoginResponse;
import com.example.demo.DTO.RequestOTPVerify;
import com.example.demo.TOTPUtil;
import com.example.demo.entitys.User;
import com.example.demo.repositorys.UserRepository;
import com.example.demo.services.AuthService;
import com.example.demo.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authen")
public class AuthenticationController {
    @Autowired
    EmailService emailService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthService authService;
    // 🔹 Đăng ký tài khoản -> Gửi OTP qua email
    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody User user) {


        try {
            // Tạo OTP từ email
            String otp = TOTPUtil.generateOtp(user.getEmail());

            // Gửi OTP qua email
            emailService.sendOtpEmail(user.getEmail(), otp);
            LoginResponse loginResponse = new LoginResponse(true, "Đăng ký thành công! Vui lòng kiểm tra email để nhận OTP.");

            return ResponseEntity.ok(loginResponse);
        } catch (Exception e) {
            LoginResponse loginResponse = new LoginResponse(false, "Loi tao OTP");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(loginResponse);
        }
    }

    // ✅ 2. API Xác thực OTP
    @PostMapping("/verify")
    public ResponseEntity<LoginResponse> verifyOtp(@RequestBody RequestOTPVerify requestOTPVerify) {
        try {
            System.out.println(requestOTPVerify.getUser().getEmail());
            // Kiểm tra OTP hợp lệ hay không
            if (TOTPUtil.verifyOtp(requestOTPVerify.getUser().getEmail(), requestOTPVerify.getOtp())) {
                userRepository.save(requestOTPVerify.getUser());
                LoginResponse loginResponse = new LoginResponse(true, "Xác thực thành công!");

                return ResponseEntity.ok(loginResponse);

            } else {
                LoginResponse loginResponse = new LoginResponse(false, "Xác thực thất bại!");

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(loginResponse);
            }
        } catch (Exception e) {
            LoginResponse loginResponse = new LoginResponse(false, "Xác thực lỗi từ hệ thống");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(loginResponse);        }
    }


    // 🔹 Đăng nhập
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody User user) {
        System.out.println(user.getPassword());
        return authService.login(user);
    }
}
