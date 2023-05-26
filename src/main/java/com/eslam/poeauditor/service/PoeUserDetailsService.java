package com.eslam.poeauditor.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.eslam.poeauditor.model.PoeUserDetails;
import com.eslam.poeauditor.repository.UserRepository;

/**
 * @author Samson Effes
 */

@Component
public class PoeUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmailId(username)
                .map(PoeUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("No user found"));
    }
}