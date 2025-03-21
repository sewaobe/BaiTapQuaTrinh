package com.example.demo.services;

import com.example.demo.DTO.LoginResponse;
import com.example.demo.entitys.User;
import com.example.demo.repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    UserRepository userRepository;
    public ResponseEntity<LoginResponse> login(User user) {
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
        if (optionalUser.isPresent()) {
            User storedUser = optionalUser.get();
            if (user.getPassword().equals(storedUser.getPassword())) {
                // Đăng nhập thành công
                LoginResponse loginResponse = new LoginResponse(true, "Đăng nhập thành công!");
                return ResponseEntity.ok(loginResponse);
            } else {
                // Sai mật khẩu
                LoginResponse loginResponse = new LoginResponse(false, "Sai mật khẩu!");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(loginResponse);
            }
        } else {
            // Email không tồn tại
            LoginResponse loginResponse = new LoginResponse(false, "Email không tồn tại!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(loginResponse);
        }
    }
}
