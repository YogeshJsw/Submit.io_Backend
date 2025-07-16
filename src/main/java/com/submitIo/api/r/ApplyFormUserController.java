package com.submitIo.api.r;

import com.submitIo.entities.ApplyFormUserEntity;
import com.submitIo.service.ApplyFormUserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/r/apply-form")
@RequiredArgsConstructor
public class ApplyFormUserController {

    private final ApplyFormUserAuthService applyFormUserAuthService;

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody ApplyFormUserEntity applyFormUserEntity){
        return applyFormUserAuthService.updateUser(applyFormUserEntity);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(){
        return applyFormUserAuthService.deleteUser();
    }
}