package com.itec.application.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String login(HttpServletRequest request, @RequestParam(name = "error") Optional<Boolean> error, Model model) {
        boolean validSession = true;
        if(request.getUserPrincipal() == null) {
            validSession = false;
        }

        model.addAttribute("alreadyLoggedIn", validSession);

        model.addAttribute("error", error.orElse(false));

        if(error.orElse(false)) {
            model.addAttribute("errorMessage", "Credentiale de authentificare invalide");
        }

        return "login";
    }
}
