package com.example.projectdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.projectdemo.model.User;
import com.example.projectdemo.service.UserService;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        // Sau khi đăng ký, tài khoản sẽ ở trạng thái chưa active (chờ admin xác nhận)
        userService.registerUser(user);
        model.addAttribute("message", "Đăng ký thành công! Vui lòng chờ admin xác nhận.");
        return "login";
    }

    @GetMapping("/change-password")
    public String changePassword(){
        return "change-password";
    }

    @PostMapping("/change-password")
    public String processChangePassword(String currentPassword, String newPassword, Model model) {
        // Xử lý thay đổi mật khẩu cho người dùng đang đăng nhập (cần bổ sung logic xác thực)
        // Đây chỉ là ví dụ đơn giản
        // ...
        model.addAttribute("message", "Mật khẩu đã được thay đổi thành công!");
        return "index";
    }
}

