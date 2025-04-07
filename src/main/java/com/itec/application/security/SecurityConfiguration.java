package com.itec.application.security;

import com.itec.application.services.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private ApplicationAuthenticationProvider applicationAuthenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests((requests) -> {
            requests.requestMatchers("/", "/login", "/register", "/register-verification", "/logout", "/error", "/application", "/report-bug", "/application/**", "/css/home.css", "/css/login.css", "/css/register.css", "/css/logout.css", "/css/register-verification.css", "/css/navbar.css", "/css/footer.css", "/css/application-dashboard.css", "/css/error.css", "/css/report-bug.css", "/js/home.js", "/js/login.js", "/js/register.js", "/js/logout.js", "/js/register-verification.js", "/js/application-dashboard.js", "/js/error.js", "/js/jquery.min.js", "/js/anime.min.js", "/js/report-bug.js", "/fonts/**", "/media/**").permitAll().anyRequest().authenticated();
        }).formLogin((login) -> {
            login.loginPage("/login").defaultSuccessUrl("/").failureUrl("/login?error=true");
        }).logout((logout) -> {
            logout.logoutUrl("/logout").logoutSuccessUrl("/").invalidateHttpSession(true).clearAuthentication(true).deleteCookies("JSESSIONID");
        }).build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(applicationAuthenticationProvider);
        return authenticationManagerBuilder.build();
    }

}
