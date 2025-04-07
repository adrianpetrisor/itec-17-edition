package com.itec.application.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogoutController {
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, Model model) {
        boolean validSession = true;
        if(request.getUserPrincipal() == null) {
            validSession = false;
        }else {
            new SecurityContextLogoutHandler().logout(request, null, null);
        }

        model.addAttribute("logout", validSession);
        return "logout";
    }
}
