package com.example.demo.services;

import com.example.demo.entitys.User;
import com.example.demo.repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    UserRepository userRepository;
    public ResponseEntity<String> login(User user) {
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());

        if (optionalUser.isPresent()) {
            User storedUser = optionalUser.get();

            // ✅ So sánh mật khẩu
            if (user.getPassword().equals(storedUser.getPassword())) {
                return ResponseEntity.ok("✅ Đăng nhập thành công!");
            } else {
                return ResponseEntity.status(401).body("❌ Sai mật khẩu!");
            }
        } else {
            return ResponseEntity.status(404).body("❌ Email không tồn tại!");
        }
    }
}
