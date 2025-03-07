package com.example.projectdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import com.example.projectdemo.model.User;
import com.example.projectdemo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
public class ProjectDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectDemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if(userRepository.findByUsername("admin") == null) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin")); // mật khẩu: admin
                admin.setName("Admin");
                admin.setRoomNumber("000");
                admin.setPhone("0123456789");
                admin.setEmail("admin@example.com");
                admin.setActive(true);
                admin.setRole("ADMIN");
                userRepository.save(admin);
            }
        };
    }

}
