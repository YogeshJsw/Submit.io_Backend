package com.submitIo.service;

import com.submitIo.entities.UploadFormUserEntity;
import com.submitIo.repository.UploadFormRepository;
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
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class UploadFormUserAuthService implements UserDetailsService {

    private final UploadFormRepository uploadFormRepository;
    private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    public ResponseEntity<String> createUser(UploadFormUserEntity uploadFormUserEntity) {
        String encodedPassword = passwordEncoder.encode(uploadFormUserEntity.getPassword());
        uploadFormUserEntity.setPassword(encodedPassword );
        uploadFormUserEntity.setRoles(Arrays.asList("UPLOAD-FORM"));
        UploadFormUserEntity saved = uploadFormRepository.save(uploadFormUserEntity);
        return new ResponseEntity<>("Created upload form user: "+saved, HttpStatus.CREATED);
    }


    public ResponseEntity<?> updateUser(UploadFormUserEntity uploadFormUserEntity) {
        if(uploadFormUserEntity.getUsername()==null)
            return new ResponseEntity<>("Please enter username",HttpStatus.BAD_REQUEST);

        Authentication authenticatedUser = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedUserName = authenticatedUser.getName();

        UploadFormUserEntity userIdDb=uploadFormRepository.findByUsername(authenticatedUserName);
        if(userIdDb!=null){
            userIdDb.setUsername(uploadFormUserEntity.getUsername());
            if(uploadFormUserEntity.getPassword()!=null)
                userIdDb.setPassword(passwordEncoder.encode(uploadFormUserEntity.getPassword()));
            uploadFormRepository.save(userIdDb);
            return new ResponseEntity<>("User updated: "+userIdDb,HttpStatus.OK);
        }
        return new ResponseEntity<>("Username does not exist",HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> deleteUser() {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null) {
            UploadFormUserEntity userEntity = uploadFormRepository.deleteByUsername(authentication.getName());
            return new ResponseEntity<>("User deleted: "+userEntity,HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("User not Logged In,",HttpStatus.BAD_REQUEST);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UploadFormUserEntity userEntity=uploadFormRepository.findByUsername(username);
        if(userEntity!=null){
            return User.builder()
                    .username(userEntity.getUsername())
                    .password(userEntity.getPassword())
                    .roles(userEntity.getRoles().toArray(new String[0]))
                    .build();
        }
        throw new UsernameNotFoundException("User not found with username: "+username);
    }
}
