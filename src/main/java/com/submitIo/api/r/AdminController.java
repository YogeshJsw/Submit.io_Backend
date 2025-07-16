package com.submitIo.api.r;

import com.submitIo.entities.ApplyFormUserEntity;
import com.submitIo.service.ApplyFormUserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ApplyFormUserAuthService applyFormUserAuthService;

    @GetMapping("all-users")
    public ResponseEntity<?> getAllUsers(){
        return applyFormUserAuthService.getAllUsers();
    }

    @PostMapping("/create-admin-user")
    public ResponseEntity<?> createAdminUser(@RequestBody ApplyFormUserEntity applyFormUserEntity){
        return applyFormUserAuthService.createAdminUser(applyFormUserEntity);
    }
}
