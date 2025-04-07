package com.itec.application.controllers;

import com.itec.application.entities.ApplicationEntity;
import com.itec.application.entities.BugEntity;
import com.itec.application.entities.EndpointEntity;
import com.itec.application.repository.ApplicationsRepository;
import com.itec.application.repository.BugRepository;
import com.itec.application.repository.EndpointRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ApplicationController {
    @Autowired
    private ApplicationsRepository applicationsRepository;

    @Autowired
    private BugRepository bugRepository;

    @Autowired
    private EndpointRepository endpointRepository;

    @GetMapping("/application")
    public String application(Model model) {
        return "error";
    }

    @GetMapping("/application/{name}")
    public ModelAndView applicationView(HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, @PathVariable String name, ModelMap model) {
        if(applicationsRepository.existsByApplicationName(name)) {
            ApplicationEntity applicationEntity = applicationsRepository.findByApplicationName(name).get();
            List<BugEntity> bugEntities = bugRepository.findAllByApplicationUUID(applicationEntity.getApplicationUUID()).get();

            boolean authenticated = false;
            boolean owner = false;
            boolean hasBugs = !bugEntities.isEmpty();

            if(httpServletRequest.getUserPrincipal() != null) {
                authenticated = true;
                model.addAttribute("userName", httpServletRequest.getUserPrincipal().getName());

                if(applicationEntity.getOwnerName().equalsIgnoreCase(httpServletRequest.getUserPrincipal().getName())) {
                    owner = true;
                }
            }else {
                model.addAttribute("userName", "Guest");
            }

            model.addAttribute("hasBugs", hasBugs);
            model.addAttribute("bugEntities", bugEntities);

            List<EndpointEntity> endpointEntities = endpointRepository.findAllByApplicationUUID(applicationEntity.getApplicationUUID()).get();
            boolean hasEndpoints = false;
            if(!endpointEntities.isEmpty()) {
                hasEndpoints = true;
            }

            model.addAttribute("hasEndpoints", hasEndpoints);
            model.addAttribute("endpointEntities", endpointEntities);

            model.addAttribute("owner", owner);
            model.addAttribute("authenticated", authenticated);
            model.addAttribute("applicationName", applicationEntity.getApplicationName());
            model.addAttribute("applicationUUID", applicationEntity.getApplicationUUID());
            return new ModelAndView("application-dashboard", model);
        }

        return new ModelAndView("error");
    }
}
