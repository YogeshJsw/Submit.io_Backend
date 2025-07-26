package com.submitIo.api.r;

import com.submitIo.entities.ApplyFormUserEmailSignupEntity;
import com.submitIo.entities.ApplyFormUserEntity;
import com.submitIo.service.authService.ApplyFormUserAuthService;
import com.submitIo.service.FormService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/apply")
@RequiredArgsConstructor
public class ApplyFormAuthApi {

    private final ApplyFormUserAuthService applyFormUserAuthService;
    private final FormService formService;

    //user details
    @PutMapping("/user-details/update")
    public ResponseEntity<String> updateUser(@RequestBody ApplyFormUserEmailSignupEntity applyFormUserEmailSignupEntity){
        return applyFormUserAuthService.updateUser(applyFormUserEmailSignupEntity);
    }

    @DeleteMapping("/user-details/delete")
    public ResponseEntity<String> deleteUser(){
        return applyFormUserAuthService.deleteUser();
    }
}