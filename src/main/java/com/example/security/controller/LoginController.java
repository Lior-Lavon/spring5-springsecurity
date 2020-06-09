package com.example.security.controller;

import com.example.security.LoginDTO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String getLoginForm(){
        return "loginform";
    }

    @GetMapping("/courses")
    public String getCourses(){
        return "courses";
    }
}
