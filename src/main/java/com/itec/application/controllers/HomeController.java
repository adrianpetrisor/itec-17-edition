package com.itec.application.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(HttpServletRequest httpServletRequest, Model model) {
        boolean authenticated = false;
        if(httpServletRequest.getUserPrincipal() != null) {
            authenticated = true;
        }

        model.addAttribute("authenticated", authenticated);
        return "home";
    }
}
