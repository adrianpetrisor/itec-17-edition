package com.itec.application.controllers;

import com.itec.application.entities.ApplicationEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SettingsSaveController {
    @PostMapping("/settings-save")
    public String saveSettings(HttpServletRequest httpServletRequest, @ModelAttribute ApplicationEntity applicationEntity, Model model) {
        return "redirect:/application/" + applicationEntity.getApplicationName();
    }
}
