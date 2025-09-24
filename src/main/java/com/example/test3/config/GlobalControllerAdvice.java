package com.example.test3.config;

import com.example.test3.model.User;
import com.example.test3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private UserService userService;

    @ModelAttribute
    public void addUserRoleToModel(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            // Ambil username dari Authentication
            String username = authentication.getName();

            // Ambil User dari database
            User user = userService.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

            // Tambahkan role ke model global
            if (user != null) {
                model.addAttribute("role", user.getRole());
            }
        }
    }
}
