package com.submitIo.service.authService;

import com.submitIo.entities.UploadFormUserEntity;
import com.submitIo.repository.UploadFormRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceUploadFormImpl  implements UserDetailsService {

    private final UploadFormRepository uploadFormRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UploadFormUserEntity userEntity=uploadFormRepository.findByUsername(username);
        if(userEntity!=null){
            log.info("Loaded user: {}, password: {}", userEntity.getUsername(), userEntity.getPassword());
            return User.builder()
                    .username(userEntity.getUsername())
                    .password(userEntity.getPassword())
                    .roles(userEntity.getRoles().toArray(new String[0]))
                    .build();
        }
        throw new UsernameNotFoundException("User not found with username: "+username);
    }
}
