package com.example.demo.controllers;

import com.example.demo.DTO.RequestOTPVerify;
import com.example.demo.TOTPUtil;
import com.example.demo.entitys.User;
import com.example.demo.repositorys.UserRepository;
import com.example.demo.services.AuthService;
import com.example.demo.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<String> register(@RequestBody User user) {


        try {
            // Tạo OTP từ email
            String otp = TOTPUtil.generateOtp(user.getEmail());

            // Gửi OTP qua email
            emailService.sendOtpEmail(user.getEmail(), otp);

            return ResponseEntity.ok("✅ Đăng ký thành công! Vui lòng kiểm tra email để nhận OTP.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("❌ Lỗi khi tạo OTP: " + e.getMessage());
        }
    }

    // ✅ 2. API Xác thực OTP
    @PostMapping("/verify")
    public ResponseEntity<String> verifyOtp(@RequestBody RequestOTPVerify requestOTPVerify) {
        try {
            // Kiểm tra OTP hợp lệ hay không
            if (TOTPUtil.verifyOtp(requestOTPVerify.getUser().getEmail(), requestOTPVerify.getOtp())) {
                userRepository.save(requestOTPVerify.getUser());
                return ResponseEntity.ok("✅ Xác thực thành công!");
            } else {
                return ResponseEntity.badRequest().body("❌ OTP không hợp lệ hoặc đã hết hạn!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("❌ Lỗi xác thực OTP: " + e.getMessage());
        }
    }


    // 🔹 Đăng nhập
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        return authService.login(user);
    }
}
