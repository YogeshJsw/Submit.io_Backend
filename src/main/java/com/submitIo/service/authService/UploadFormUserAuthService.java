package com.submitIo.service.authService;

import com.submitIo.entities.UploadFormUserEntity;
import com.submitIo.repository.UploadFormRepository;
import com.submitIo.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Slf4j
@Service
@RequiredArgsConstructor
public class UploadFormUserAuthService{

    private final UploadFormRepository uploadFormRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceUploadFormImpl userDetailsServiceUploadFormImpl;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<String> signup(UploadFormUserEntity uploadFormUserEntity) {
        String encodedPassword = passwordEncoder.encode(uploadFormUserEntity.getPassword());
        uploadFormUserEntity.setPassword(encodedPassword );
        uploadFormUserEntity.setRoles(Arrays.asList("UPLOAD"));
        UploadFormUserEntity saved = uploadFormRepository.save(uploadFormUserEntity);
        return new ResponseEntity<>("Created upload form user: "+saved, HttpStatus.CREATED);
    }


    public ResponseEntity<String> updateUser(UploadFormUserEntity uploadFormUserEntity) {
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

    public ResponseEntity<String> deleteUser() {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null) {
            UploadFormUserEntity userEntity = uploadFormRepository.deleteByUsername(authentication.getName());
            return new ResponseEntity<>("User deleted: "+userEntity,HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("User not Logged In,",HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> login(UploadFormUserEntity uploadFormUserEntity) {
        try {
            log.info("Authenticating upload form user: {}", uploadFormUserEntity.getUsername());
//            System.out.println("Raw password: " + uploadFormUserEntity.getPassword());
//            System.out.println("DB Encoded password: " + userDetails.getPassword());
//            System.out.println("Password match? " + passwordEncoder.matches(uploadFormUserEntity.getPassword(), userDetails.getPassword()));

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(uploadFormUserEntity.getUsername(),uploadFormUserEntity.getPassword()));
            UserDetails userDetails = userDetailsServiceUploadFormImpl.loadUserByUsername(uploadFormUserEntity.getUsername());

            String jwt = jwtUtil.generateToken(userDetails.getUsername());

            return new ResponseEntity<>(jwt, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Authentication failed: ", e);
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.BAD_REQUEST);
        }
    }
}
