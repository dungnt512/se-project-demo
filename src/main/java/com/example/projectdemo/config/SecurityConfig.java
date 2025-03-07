package com.example.projectdemo.config;

import com.example.projectdemo.model.User;
import com.example.projectdemo.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/register", "/login", "/css/**", "/assets/**").permitAll()
                        // URL admin chỉ cho admin (lưu ý: khi dùng hasRole thì Spring Security sẽ tự thêm tiền tố ROLE_)
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/index", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            User user = userRepository.findByUsername(username);
            if(user != null && user.isActive()) {
                UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(username);
                builder.password(user.getPassword());
                // Nếu role trong DB là "ADMIN", thì khi xác thực sẽ được coi là ROLE_ADMIN
                builder.roles(user.getRole());
                return builder.build();
            } else {
                throw new UsernameNotFoundException("User không tồn tại hoặc chưa được kích hoạt");
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


