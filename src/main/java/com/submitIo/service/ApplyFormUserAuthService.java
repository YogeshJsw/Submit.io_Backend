package com.submitIo.service;

import com.submitIo.entities.ApplyFormUserEntity;
import com.submitIo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ApplyFormUserAuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
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

    public ResponseEntity<?> getAllUsers() {
        List<ApplyFormUserEntity> all = userRepository.findAll();
        if(all!=null && !all.isEmpty())
            return new ResponseEntity<>(all,HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<ApplyFormUserEntity> createUser(ApplyFormUserEntity applyFormUserEntity) {
        String encodedPassword = passwordEncoder.encode(applyFormUserEntity.getPassword());
        applyFormUserEntity.setPassword(encodedPassword );
        applyFormUserEntity.setRoles(Arrays.asList("USER"));
        ApplyFormUserEntity saved = userRepository.save(applyFormUserEntity);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    public ResponseEntity<?> updateUser(ApplyFormUserEntity applyFormUserEntity) {

        Authentication authenticatedUser = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedUserName = authenticatedUser.getName();

        ApplyFormUserEntity userIdDb=userRepository.findByUsername(authenticatedUserName);
        if(userIdDb!=null){
            userIdDb.setUsername(applyFormUserEntity.getUsername());
            userIdDb.setPassword(passwordEncoder.encode(applyFormUserEntity.getPassword()));
            userRepository.save(userIdDb);
            return new ResponseEntity<>("User updated: "+userIdDb,HttpStatus.OK);
        }
        return new ResponseEntity<>("Username does not exist",HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> deleteUser() {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null) {
            ApplyFormUserEntity applyFormUserEntity = userRepository.deleteByUsername(authentication.getName());
            return new ResponseEntity<>("User deleted: "+ applyFormUserEntity,HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("User not loggedIn,",HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> createAdminUser(ApplyFormUserEntity applyFormUserEntity) {
        String encodedPassword = passwordEncoder.encode(applyFormUserEntity.getPassword());
        applyFormUserEntity.setPassword(encodedPassword );
        applyFormUserEntity.setRoles(Arrays.asList("USER","ADMIN"));
        ApplyFormUserEntity saved = userRepository.save(applyFormUserEntity);
        return new ResponseEntity<>("Created Admin user: "+saved, HttpStatus.CREATED);
    }
}
