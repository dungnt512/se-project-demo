package com.example.projectdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.projectdemo.model.User;
import com.example.projectdemo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(User user) {
        // Đặt vai trò mặc định và trạng thái không active
        user.setRole("USER");
        user.setActive(false); // Chờ xác nhận của admin
        // Mã hóa mật khẩu
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public void changePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Admin xác nhận tài khoản
    public void confirmUser(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if(user != null) {
            user.setActive(true);
            userRepository.save(user);
        }
    }
}

