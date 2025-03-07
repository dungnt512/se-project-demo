package com.example.projectdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.projectdemo.repository.UserRepository;
import com.example.projectdemo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.projectdemo.service.UserService;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/admin/confirm")
    public String confirmList(Model model) {
        List<User> users = userRepository.findAll();
        // Lọc các tài khoản chưa được kích hoạt
        users.removeIf(User::isActive);
        model.addAttribute("users", users);
        return "admin-confirm";
    }

    @PostMapping("/admin/confirm/{id}")
    public String confirmUser(@PathVariable("id") Long id) {
        userService.confirmUser(id);
        return "redirect:/admin/confirm";
    }
}

