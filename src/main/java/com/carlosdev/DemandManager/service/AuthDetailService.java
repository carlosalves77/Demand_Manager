package com.carlosdev.DemandManager.service;

import com.carlosdev.DemandManager.repository.UserAuthRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthDetailService implements UserDetailsService {

    private final UserAuthRepository userAuthRepository;

    public AuthDetailService(UserAuthRepository userAuthRepository) {
        this.userAuthRepository = userAuthRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userAuthRepository.findUserByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException(username)
        );
    }
}
