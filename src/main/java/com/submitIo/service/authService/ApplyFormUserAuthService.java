package com.submitIo.service.authService;

import com.submitIo.entities.ApplyFormUserEmailSignupEntity;
import com.submitIo.repository.ApplyFormEmailSignupRepository;
import com.submitIo.repository.ApplyFormUserUnverifiedRepository;
import com.submitIo.repository.ApplyFormUserVerifiedRepository;
import com.submitIo.requestDto.ApplyFormUserEmailSignupRequestDto;
import com.submitIo.service.GmailService;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ApplyFormUserAuthService{

//    private final UserRepository userRepository;
    private final ApplyFormUserUnverifiedRepository unverifiedRepo;
    private final ApplyFormUserVerifiedRepository verifiedRepo;
    private final GmailService gmailService;
    private final ApplyFormEmailSignupRepository applyFormEmailSignupRepository;
    private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceApplyFormImpl userDetailsServiceApplyFormImpl;

    public ResponseEntity<?> getAllUsers() {
        List<ApplyFormUserEmailSignupEntity> all = applyFormEmailSignupRepository.findAll();
        if(all!=null && !all.isEmpty())
            return new ResponseEntity<>(all,HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<ApplyFormUserEmailSignupEntity> signup(ApplyFormUserEmailSignupRequestDto applyFormUserEmailSignupRequestDto) {

        if(verifiedRepo.existsByEmail(applyFormUserEmailSignupRequestDto.getEmail()) || unverifiedRepo.existsByEmail(applyFormUserEmailSignupRequestDto.getEmail())){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        String encodedPassword = passwordEncoder.encode(applyFormUserEmailSignupRequestDto.getPassword());
        String otp=
        applyFormUserEmailSignupRequestDto.setPassword(encodedPassword );
        applyFormUserEmailSignupRequestDto.setRoles(Arrays.asList("USER"));

        ApplyFormUserEmailSignupEntity saved = applyFormEmailSignupRepository.save(applyFormUserEmailSignupEntity);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    public ResponseEntity<String> updateUser(ApplyFormUserEmailSignupEntity applyFormUserEmailSignupEntity) {
        if(applyFormUserEmailSignupEntity.getUsername()==null)
            return new ResponseEntity<>("Please enter username",HttpStatus.BAD_REQUEST);
        Authentication authenticatedUser = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedUserName = authenticatedUser.getName();

        ApplyFormUserEmailSignupEntity userIdDb=applyFormEmailSignupRepository.findByUsername(authenticatedUserName);
        if(userIdDb!=null){
            userIdDb.setUsername(applyFormUserEmailSignupEntity.getUsername());
            if(applyFormUserEmailSignupEntity.getPassword()!=null)
                userIdDb.setPassword(passwordEncoder.encode(applyFormUserEmailSignupEntity.getPassword()));
            applyFormEmailSignupRepository.save(userIdDb);
            return new ResponseEntity<>("User updated: "+userIdDb,HttpStatus.OK);
        }
        return new ResponseEntity<>("Username does not exist",HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> deleteUser() {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null) {
            ApplyFormUserEmailSignupEntity applyFormUserEntity = applyFormEmailSignupRepository.deleteByUsername(authentication.getName());
            return new ResponseEntity<>("User deleted: "+ applyFormUserEntity,HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("User not loggedIn,",HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> createAdminUser(ApplyFormUserEmailSignupEntity applyFormUserEmailSignupEntity) {
        String encodedPassword = passwordEncoder.encode(applyFormUserEmailSignupEntity.getPassword());
        applyFormUserEmailSignupEntity.setPassword(encodedPassword );
        applyFormUserEmailSignupEntity.setRoles(Arrays.asList("USER","ADMIN"));
        ApplyFormUserEmailSignupEntity saved = applyFormEmailSignupRepository.save(applyFormUserEmailSignupEntity);
        return new ResponseEntity<>("Created Admin user: "+saved, HttpStatus.CREATED);
    }

    public ResponseEntity<String> login(ApplyFormUserEmailSignupEntity applyFormUserEmailSignupEntity) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(applyFormUserEmailSignupEntity.getUsername(), applyFormUserEmailSignupEntity.getPassword()));
            UserDetails userDetails = userDetailsServiceApplyFormImpl.loadUserByUsername(applyFormUserEmailSignupEntity.getUsername());
            String jwt=jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt,HttpStatus.OK);
        }
        catch (Exception e) {
            log.error("Exception occured while creating Authentication token for upload form login: "+e);
            return new ResponseEntity<>("Incorrect username or password",HttpStatus.BAD_REQUEST);
        }
    }
}
