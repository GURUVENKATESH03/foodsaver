package com.project.foodsaver.security;

import com.project.foodsaver.entity.ProfileEntity;
import com.project.foodsaver.repositories.ProfileRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ProfileRepo profileRepo;

    public UserDetailsServiceImpl(ProfileRepo profileRepo) {
        this.profileRepo = profileRepo;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ProfileEntity profile = profileRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return User.builder()
                .username(profile.getEmail())
                .password(profile.getPassword())
                .authorities(Collections.singletonList(
                        new SimpleGrantedAuthority("ROLE_" + profile.getRole().getRoleName())
                ))
                .accountLocked(profile.getAccountLocked())
                .disabled(!profile.getIsActive())
                .build();
    }
}