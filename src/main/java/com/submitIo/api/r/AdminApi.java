package com.submitIo.api.r;

import com.submitIo.entities.ApplyFormUserEmailSignupEntity;
import com.submitIo.entities.ApplyFormUserEntity;
import com.submitIo.service.authService.ApplyFormUserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminApi {

    private final ApplyFormUserAuthService applyFormUserAuthService;

    @GetMapping("all-users")
    public ResponseEntity<?> getAllUsers(){
        return applyFormUserAuthService.getAllUsers();
    }

    @PostMapping("/create-admin-user")
    public ResponseEntity<?> createAdminUser(@RequestBody ApplyFormUserEmailSignupEntity applyFormUserEmailSignupEntity){
        return applyFormUserAuthService.createAdminUser(applyFormUserEmailSignupEntity);
    }
}
