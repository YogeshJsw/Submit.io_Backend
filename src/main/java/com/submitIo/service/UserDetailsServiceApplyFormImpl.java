package com.submitIo.service;

import com.submitIo.entities.ApplyFormUserEntity;
import com.submitIo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceApplyFormImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplyFormUserEntity applyFormUserEntity =userRepository.findByUsername(username);
        if(applyFormUserEntity !=null){
            return User.builder()
                    .username(applyFormUserEntity.getUsername())
                    .password(applyFormUserEntity.getPassword())
                    .roles(applyFormUserEntity.getRoles().toArray(new String[0]))
                    .build();
        }
        throw new UsernameNotFoundException("User not found with username: "+username);
    }
}
