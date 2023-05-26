package com.eslam.poeauditor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.eslam.poeauditor.service.UserService;

@Controller
public class LandingController {
    
    @Autowired
    private UserService userService;

    @GetMapping("/poeaccess")
    public String showUserList(
        @RequestParam("code") String code, 
        @RequestParam("state") String state, 
        Model model) {
            
        model.addAttribute("users", userService.listUsers());
        return "index.html";
    }
}
