package com.itec.application.controllers;

import com.itec.application.entities.ApplicationEntity;
import com.itec.application.entities.UserEntity;
import com.itec.application.objects.ApplicationCredentials;
import com.itec.application.objects.Default;
import com.itec.application.objects.Verifier;
import com.itec.application.repository.ApplicationsRepository;
import com.itec.application.repository.SettingsRepository;
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
public class CreateApplicationController {
    @Autowired
    private ApplicationsRepository applicationsRepository;

    @Autowired
    private SettingsRepository settingsRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/create-application")
    public ModelAndView createApplication(ModelMap model, @ModelAttribute("error")Optional<String> error, @ModelAttribute("errorMessage")Optional<String> errorMessage) {
        if(!error.get().equalsIgnoreCase("true")) {
            model.addAttribute("error", false);
        }

        model.addAttribute("applicationCredentials", new ApplicationCredentials());
        return new ModelAndView("create-application", model);
    }

    @PostMapping("/create-application")
    public RedirectView createApplication(HttpServletRequest httpServletRequest, @ModelAttribute ApplicationCredentials applicationCredentials, RedirectAttributes redirectAttributes, Model model) {
        if(!(applicationCredentials.getApplicationName().trim().isEmpty() || applicationCredentials.getApplicationPhoto().trim().isEmpty() || applicationCredentials.getApplicationDescription().trim().isEmpty())) {
            if(!applicationsRepository.existsByApplicationName(applicationCredentials.getApplicationName())) {
                if(!applicationCredentials.getApplicationName().contains(" ")) {
                    if(Verifier.checkString(applicationCredentials.getApplicationName(), false) && Verifier.checkString(applicationCredentials.getApplicationDescription(), true)) {
                        if(Verifier.checkDescriptionConvention(applicationCredentials.getApplicationDescription())) {
                            if(Verifier.checkImgurLink(applicationCredentials.getApplicationPhoto())) {
                                UserEntity userEntity = userRepository.findByUsername(httpServletRequest.getUserPrincipal().getName()).get();
                                ApplicationEntity applicationEntity = new ApplicationEntity(UUID.randomUUID().toString(), userEntity.getUserUUID(), userEntity.getUsername(), applicationCredentials.getApplicationName(),applicationCredentials.getApplicationPhoto(), applicationCredentials.getApplicationDescription());
                                applicationsRepository.save(applicationEntity);
                                settingsRepository.save(Default.getDefaultSettings(applicationEntity.getApplicationUUID()));
                                return new RedirectView("dashboard");
                            }else {
                                redirectAttributes.addFlashAttribute("error", "true");
                                redirectAttributes.addFlashAttribute("errorMessage", "Link-ul de imgur este invalid.");
                            }
                        }else {
                            redirectAttributes.addFlashAttribute("error", "true");
                            redirectAttributes.addFlashAttribute("errorMessage", "Descrierea trebuie sa aiba intre 32 si 128 de caractere.");
                        }
                    }else {
                        redirectAttributes.addFlashAttribute("error", "true");
                        redirectAttributes.addFlashAttribute("errorMessage", "Numele sau descrierea aplicatiei trebuie sa contina doar litere.");
                    }
                }else {
                    redirectAttributes.addFlashAttribute("error", "true");
                    redirectAttributes.addFlashAttribute("errorMessage", "Numele aplicatiei nu are voie sa contina spatii.");
                }
            }else {
                redirectAttributes.addFlashAttribute("error", "true");
                redirectAttributes.addFlashAttribute("errorMessage", "Aceasta aplicatie deja exista.");
            }
        }else {
            redirectAttributes.addFlashAttribute("error", "true");
            redirectAttributes.addFlashAttribute("errorMessage", "Toate campurile trebuie sa fie completate.");
        }

        return new RedirectView("create-application");
    }
}
