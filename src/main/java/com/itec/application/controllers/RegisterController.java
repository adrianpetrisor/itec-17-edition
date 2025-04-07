package com.itec.application.controllers;

import com.itec.application.entities.UserEntity;
import com.itec.application.objects.RegistrationCredentials;
import com.itec.application.objects.Verifier;
import com.itec.application.repository.UserRepository;
import com.itec.application.services.UserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
public class RegisterController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/register")
    public ModelAndView register(HttpServletRequest request, ModelMap model, @ModelAttribute("error") Optional<String> error, @ModelAttribute("errorMessage")Optional<String> errorMessage) {
        boolean validSession = true;
        if(request.getUserPrincipal() == null) {
            validSession = false;
        }

        if(error.get().isEmpty()) {
            model.addAttribute("error", "false");
        }

        model.addAttribute("alreadyLoggedIn", validSession);
        model.addAttribute("registrationCredentials", new RegistrationCredentials());
        return new ModelAndView("register", model);
    }

    @PostMapping("/register")
    public RedirectView register(@ModelAttribute RegistrationCredentials registrationCredentials, RedirectAttributes redirectAttributes, Model model) {
        if(Verifier.checkNameConvention(registrationCredentials.getUsername())) {
            if(!userRepository.existsByUsername(registrationCredentials.getUsername())) {
                if(!userRepository.existsByEmail(registrationCredentials.getEmail())) {
                    UserEntity userEntity = new UserEntity(UUID.randomUUID().toString(), registrationCredentials.getUsername(), registrationCredentials.getEmail(), bCryptPasswordEncoder.encode(registrationCredentials.getPassword()));
                    userDetailsService.createUser(userEntity);

                    return new RedirectView("login");
                }else {
                    redirectAttributes.addFlashAttribute("error", "true");
                    redirectAttributes.addFlashAttribute("errorMessage", "Email-ul introdus este deja utilizat.");
                }
            }else {
                redirectAttributes.addFlashAttribute("error", "true");
                redirectAttributes.addFlashAttribute("errorMessage", "Numele acesta de utilizator deja exista.");
            }
        }else {
            redirectAttributes.addFlashAttribute("error", "true");
            redirectAttributes.addFlashAttribute("errorMessage", "Numele trebuie sa fie doar de 16 caractere, litere.");
        }

        return new RedirectView("register");
    }

    @GetMapping("register-verification")
    public String registerVerification(Model model) {
        return "register-verification";
    }
}
