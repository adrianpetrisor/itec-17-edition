package com.itec.application.controllers;

import com.itec.application.entities.ApplicationEntity;
import com.itec.application.entities.EndpointEntity;
import com.itec.application.entities.UserEntity;
import com.itec.application.objects.EndpointCredentials;
import com.itec.application.objects.Verifier;
import com.itec.application.repository.ApplicationsRepository;
import com.itec.application.repository.EndpointRepository;
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
public class EndpointController {
    @Autowired
    private ApplicationsRepository applicationsRepository;

    @Autowired
    private EndpointRepository endpointRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/create-endpoint")
    public ModelAndView createEndpoint(HttpServletRequest httpServletRequest, @ModelAttribute(name = "applicationUUID") Optional<String> applicationUUID, ModelMap model) {
        UserEntity userEntity = userRepository.findByUsername(httpServletRequest.getUserPrincipal().getName()).get();

        if(applicationUUID.get().isEmpty()) {
            return new ModelAndView("error");
        }

        if(applicationsRepository.existsByApplicationUUID(applicationUUID.get())) {
            ApplicationEntity applicationEntity = applicationsRepository.findByApplicationUUID(applicationUUID.get()).get();

            if(userEntity.getUserUUID().equalsIgnoreCase(applicationEntity.getOwnerID())) {
                model.addAttribute("endpointCredentials", new EndpointCredentials());
                return new ModelAndView("create-endpoint", model);
            }else {
                model.addAttribute("error", "true");
                model.addAttribute("errorMessage", "Nu detii aceasta aplicatie.");
                return new ModelAndView("create-endpoint", model);
            }
        }else {
            return new ModelAndView("error");
        }
    }

    @PostMapping("/create-endpoint")
    public RedirectView createEndpoint(HttpServletRequest httpServletRequest, @ModelAttribute EndpointCredentials endpointCredentials, RedirectAttributes redirectAttributes, Model model) {
        UserEntity userEntity = userRepository.findByUsername(httpServletRequest.getUserPrincipal().getName()).get();

        if(applicationsRepository.existsByApplicationName(endpointCredentials.getApplicationName())) {
            ApplicationEntity applicationEntity = applicationsRepository.findByApplicationName(endpointCredentials.getApplicationName()).get();
            if(applicationEntity.getOwnerID().equalsIgnoreCase(userEntity.getUserUUID())) {
                if(Verifier.checkNameConvention(endpointCredentials.getTitle())) {
                    if(endpointCredentials.getType().equalsIgnoreCase("URL")) {
                        if(!Verifier.pingHttpAddress(endpointCredentials.getValue())) {
                            redirectAttributes.addFlashAttribute("error", "true");
                            redirectAttributes.addFlashAttribute("errorMessage", "Endpoint-ul nu raspunde.");
                            return new RedirectView("/create-endpoint?applicationUUID=" + applicationEntity.getApplicationUUID());
                        }
                    }

                    EndpointEntity endpointEntity = new EndpointEntity(UUID.randomUUID().toString(), applicationEntity.getApplicationUUID(), endpointCredentials.getTitle(), endpointCredentials.getType(), endpointCredentials.getValue());
                    endpointRepository.save(endpointEntity);

                    redirectAttributes.addFlashAttribute("notification", "true");
                    redirectAttributes.addFlashAttribute("notificationMessage", "Endpoint-ul a fost creeat cu success.");

                    return new RedirectView("/application/" + applicationEntity.getApplicationName());
                }else {
                    redirectAttributes.addFlashAttribute("error", "true");
                    redirectAttributes.addFlashAttribute("errorMessage", "Nume invalid");
                    return new RedirectView("/create-endpoint?applicationUUID=" + applicationEntity.getApplicationUUID());
                }
            }else {
                redirectAttributes.addFlashAttribute("error", "true");
                redirectAttributes.addFlashAttribute("errorMessage", "Aplicatie invalida");
                return new RedirectView("/create-endpoint?applicationUUID=" + applicationEntity.getApplicationUUID());
            }
        }else {
            return new RedirectView("/error");
        }
    }

    @GetMapping("/delete-endpoint")
    public RedirectView deleteEndpoint(HttpServletRequest httpServletRequest, @ModelAttribute(name = "endpointUUID") Optional<String> endpointUUID, RedirectAttributes redirectAttributes) {
        UserEntity userEntity = userRepository.findByUsername(httpServletRequest.getUserPrincipal().getName()).get();

        if(endpointUUID.get().isEmpty()) {
            return new RedirectView("/error");
        }

        if(endpointRepository.existsByEndpointUUID(endpointUUID.get())) {
            EndpointEntity endpointEntity = endpointRepository.findByEndpointUUID(endpointUUID.get()).get();
            ApplicationEntity applicationEntity = applicationsRepository.findByApplicationUUID(endpointEntity.getApplicationUUID()).get();

            if(userEntity.getUserUUID().equalsIgnoreCase(applicationEntity.getOwnerID())) {
                endpointRepository.delete(endpointEntity);

                redirectAttributes.addFlashAttribute("notification", "true");
                redirectAttributes.addFlashAttribute("notificationMessage", "Endpoint deleted successfully.");

                return new RedirectView("/application/" + applicationEntity.getApplicationName());
            }else {
                return new RedirectView("/error");
            }
        }else {
            return new RedirectView("/error");
        }
    }
}
