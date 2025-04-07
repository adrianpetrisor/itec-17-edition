package com.itec.application.security;

import com.itec.application.entities.UserEntity;
import com.itec.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ApplicationAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        if(userRepository.existsByUsername(username)) {
            if(bCryptPasswordEncoder.matches(authentication.getCredentials().toString(), userRepository.findByUsername(username).get().getPassword())) {
                UserEntity userEntity = userRepository.findByUsername(username).get();
                return new UsernamePasswordAuthenticationToken(userEntity, authentication.getCredentials(), userEntity.getAuthorities());
            }
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
