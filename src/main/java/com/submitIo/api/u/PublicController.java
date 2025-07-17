package com.submitIo.api.u;

import com.submitIo.entities.FormEntity;
import com.submitIo.entities.UploadFormUserEntity;
import com.submitIo.entities.ApplyFormUserEntity;
import com.submitIo.service.UploadFormUserAuthService;
import com.submitIo.service.ApplyFormUserAuthService;
import com.submitIo.service.FormService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/u")
@RequiredArgsConstructor
public class PublicController {

    private final ApplyFormUserAuthService applyFormUserAuthService;
    private final FormService formService;
    private final UploadFormUserAuthService uploadFormUserAuthService;

    @PostMapping("/upload-form/signup")
    public ResponseEntity<String> uploadFormSignup(@RequestBody UploadFormUserEntity uploadFormUserEntity){
        return uploadFormUserAuthService.signup(uploadFormUserEntity);
    }

    @PostMapping("/upload-form/login")
    public ResponseEntity<String> uploadFormLogin(@RequestBody UploadFormUserEntity uploadFormUserEntity){
        return uploadFormUserAuthService.login(uploadFormUserEntity);
    }

    @PostMapping("/apply-form/signup")
    public ResponseEntity<ApplyFormUserEntity> applyFormSignup(@RequestBody ApplyFormUserEntity applyFormUserEntity){
        return applyFormUserAuthService.signup(applyFormUserEntity);
    }

    @PostMapping("/apply-form/login")
    public ResponseEntity<String> applyFormLogin(@RequestBody ApplyFormUserEntity applyFormUserEntity){
        return applyFormUserAuthService.login(applyFormUserEntity);
    }
}
