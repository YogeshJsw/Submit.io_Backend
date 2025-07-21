package com.submitIo.api.u;

import com.submitIo.entities.UploadFormUserEntity;
import com.submitIo.entities.ApplyFormUserEntity;
import com.submitIo.service.authService.UploadFormUserAuthService;
import com.submitIo.service.authService.ApplyFormUserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/u")
@RequiredArgsConstructor
public class PublicController {

    private final ApplyFormUserAuthService applyFormUserAuthService;
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
