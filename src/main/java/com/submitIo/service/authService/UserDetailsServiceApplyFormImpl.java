package com.submitIo.service.authService;

import com.submitIo.entities.ApplyFormUserEmailSignupEntity;
import com.submitIo.repository.ApplyFormEmailSignupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceApplyFormImpl implements UserDetailsService {

//    private final UserRepository userRepository;
    private final ApplyFormEmailSignupRepository applyFormEmailSignupRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplyFormUserEmailSignupEntity applyFormUserEmailSignupEntity =applyFormEmailSignupRepository.findByUsername(username);
        if(applyFormUserEmailSignupEntity !=null){
            return User.builder()
                    .username(applyFormUserEmailSignupEntity.getUsername())
                    .password(applyFormUserEmailSignupEntity.getPassword())
                    .roles(applyFormUserEmailSignupEntity.getRoles().toArray(new String[0]))
                    .build();
        }
        throw new UsernameNotFoundException("User not found with username: "+username);
    }
}
