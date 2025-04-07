package com.itec.application.controllers;

import com.itec.application.entities.ApplicationEntity;
import com.itec.application.entities.UserEntity;
import com.itec.application.repository.ApplicationsRepository;
import com.itec.application.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DashboardController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationsRepository applicationsRepository;

    @GetMapping("/dashboard")
    public String dashboard(HttpServletRequest httpServletRequest, Model model) {
        UserEntity userEntity = userRepository.findByUsername(httpServletRequest.getUserPrincipal().getName()).get();
        List<ApplicationEntity> applicationEntities = applicationsRepository.findAllByOwnerID(userEntity.getUserUUID()).get();

        if(!applicationEntities.isEmpty()) {
            model.addAttribute("applications", applicationEntities);
            model.addAttribute("hasApplications", true);
        }else {
            model.addAttribute("hasApplications", false);
        }

        return "dashboard";
    }
}
