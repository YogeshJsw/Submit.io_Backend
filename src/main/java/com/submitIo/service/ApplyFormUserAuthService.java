package com.submitIo.service;

import com.submitIo.entities.ApplyFormUserEntity;
import com.submitIo.repository.UserRepository;
import com.submitIo.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
@Slf4j
public class ApplyFormUserAuthService{

    private final UserRepository userRepository;
    private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceApplyFormImpl userDetailsServiceApplyFormImpl;

    public ResponseEntity<?> getAllUsers() {
        List<ApplyFormUserEntity> all = userRepository.findAll();
        if(all!=null && !all.isEmpty())
            return new ResponseEntity<>(all,HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<ApplyFormUserEntity> signup(ApplyFormUserEntity applyFormUserEntity) {
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

    public ResponseEntity<String> login(ApplyFormUserEntity applyFormUserEntity) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(applyFormUserEntity.getUsername(), applyFormUserEntity.getPassword()));
            UserDetails userDetails = userDetailsServiceApplyFormImpl.loadUserByUsername(applyFormUserEntity.getUsername());
            String jwt=jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt,HttpStatus.OK);
        }
        catch (Exception e) {
            log.error("Exception occured while creating Authentication token for upload form login: "+e);
            return new ResponseEntity<>("Incorrect username or password",HttpStatus.BAD_REQUEST);
        }
    }
}
