package com.itec.application.controllers;

import com.itec.application.entities.ApplicationEntity;
import com.itec.application.entities.BugEntity;
import com.itec.application.entities.UserEntity;
import com.itec.application.objects.BugCredentials;
import com.itec.application.objects.Verifier;
import com.itec.application.repository.ApplicationsRepository;
import com.itec.application.repository.BugRepository;
import com.itec.application.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;
import java.util.UUID;

@Controller
public class BugController {
    @Autowired
    private ApplicationsRepository applicationsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BugRepository bugRepository;

    @GetMapping("/report-bug")
    public ModelAndView reportBug(HttpServletRequest httpServletRequest, @ModelAttribute("applicationName") Optional<String> applicationName, ModelMap model, @ModelAttribute("error")Optional<String> error, @ModelAttribute("errorMessage")Optional<String> errorMessage) {
        boolean authenticated = false;
        if(httpServletRequest.getUserPrincipal() != null) {
            authenticated = true;
        }

        if(!error.get().equalsIgnoreCase("true")) {
            model.addAttribute("error", false);
        }

        model.addAttribute("bugCredentials", new BugCredentials());

        model.addAttribute("applicationName", applicationName.get());
        model.addAttribute("authenticated", authenticated);
        return new ModelAndView("report-bug");
    }

    @PostMapping("/report-bug")
    public RedirectView reportBug(HttpServletRequest httpServletRequest, @ModelAttribute BugCredentials bugCredentials, RedirectAttributes redirectAttributes, Model model) {
        boolean authenticated = false;
        if(httpServletRequest.getUserPrincipal() != null) {
            authenticated = true;
        }

        if(applicationsRepository.existsByApplicationName(bugCredentials.getApplicationName())) {
            ApplicationEntity applicationEntity = applicationsRepository.findByApplicationName(bugCredentials.getApplicationName()).get();
            String email = bugCredentials.getEmail();

            if(authenticated) {
                UserEntity userEntity = userRepository.findByUsername(httpServletRequest.getUserPrincipal().getName()).get();
                if(userEntity.getUserUUID().equalsIgnoreCase(applicationEntity.getOwnerID())) {
                    redirectAttributes.addFlashAttribute("error", "true");
                    redirectAttributes.addFlashAttribute("errorMessage", "Nu puteti raporta un bug la propria aplicatie.");

                    return new RedirectView("/report-bug");
                }else {
                    email = userEntity.getEmail();
                }
            }

            if(email.equalsIgnoreCase(userRepository.findByUsername(applicationEntity.getOwnerName()).get().getEmail())) {
                redirectAttributes.addFlashAttribute("error", "true");
                redirectAttributes.addFlashAttribute("errorMessage", "Nu puteti raporta un bug la propria aplicatie.");

                return new RedirectView("/report-bug");
            }

            if(!bugRepository.existsByEmail(email)) {
                if (Verifier.checkNameConvention(bugCredentials.getTitle())) {
                    BugEntity bugEntity = new BugEntity(UUID.randomUUID().toString(), applicationEntity.getApplicationUUID(), email, bugCredentials.getTitle(), bugCredentials.getType(), bugCredentials.getDescription());
                    bugRepository.save(bugEntity);

                    redirectAttributes.addFlashAttribute("notification", "true");
                    redirectAttributes.addFlashAttribute("notificationMessage", "Bug raportat cu success.");

                    return new RedirectView("/application/" + applicationEntity.getApplicationName());
                } else {
                    redirectAttributes.addFlashAttribute("error", "true");
                    redirectAttributes.addFlashAttribute("errorMessage", "Nume invalid.");

                    return new RedirectView("/report-bug");
                }
            }else {
                redirectAttributes.addFlashAttribute("error", "true");
                redirectAttributes.addFlashAttribute("errorMessage", "Ati raportat deja un bug.");

                return new RedirectView("/report-bug");
            }
        }else {
            redirectAttributes.addFlashAttribute("error", "true");
            redirectAttributes.addFlashAttribute("errorMessage", "Aplicatia specificata nu exista.");

            return new RedirectView("/report-bug");
        }
    }

    @GetMapping("/view-bug")
    public ModelAndView viewBug(HttpServletRequest httpServletRequest, @ModelAttribute("bugUUID") Optional<String> bugUUID, ModelMap model) {
        UserEntity userEntity = userRepository.findByUsername(httpServletRequest.getUserPrincipal().getName()).get();

        if(!bugUUID.get().isEmpty()) {
            if (bugRepository.existsByBugUUID(bugUUID.get())) {

                BugEntity bugEntity = bugRepository.findByBugUUID(bugUUID.get()).get();
                ApplicationEntity applicationEntity = applicationsRepository.findByApplicationUUID(bugEntity.getApplicationUUID()).get();

                if(userEntity.getUserUUID().equalsIgnoreCase(applicationEntity.getOwnerID())) {
                    model.addAttribute("bugEntity", bugEntity);
                    model.addAttribute("applicationName", applicationEntity.getApplicationName());
                    return new ModelAndView("view-bug", model);
                }else {
                    return new ModelAndView("error");
                }
            } else {
                return new ModelAndView("error");
            }
        }else {
            return new ModelAndView("error");
        }
    }

    @GetMapping("/delete-bug")
    public RedirectView deleteBug(HttpServletRequest httpServletRequest, @ModelAttribute("bugUUID") Optional<String> bugUUID, RedirectAttributes redirectAttributes) {
        UserEntity userEntity = userRepository.findByUsername(httpServletRequest.getUserPrincipal().getName()).get();

        if(!bugUUID.get().isEmpty()) {
            if (bugRepository.existsByBugUUID(bugUUID.get())) {

                BugEntity bugEntity = bugRepository.findByBugUUID(bugUUID.get()).get();
                ApplicationEntity applicationEntity = applicationsRepository.findByApplicationUUID(bugEntity.getApplicationUUID()).get();

                if(userEntity.getUserUUID().equalsIgnoreCase(applicationEntity.getOwnerID())) {
                    redirectAttributes.addFlashAttribute("notification", "true");
                    redirectAttributes.addFlashAttribute("notificationMessage", "Bug-ul a fost marcat ca rezolvat.");
                    bugRepository.delete(bugEntity);

                    return new RedirectView("/application/" + applicationEntity.getApplicationName());
                }else {
                    return new RedirectView("/error");
                }
            } else {
                return new RedirectView("/error");
            }
        }else {
            return new RedirectView("/error");
        }
    }
}
