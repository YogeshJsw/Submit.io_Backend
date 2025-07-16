package com.submitIo.api.r;

import com.submitIo.entities.UploadFormUserEntity;
import com.submitIo.service.UploadFormUserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/r/upload-form")
@RequiredArgsConstructor
public class UploadFormUserController {

    private final UploadFormUserAuthService uploadFormUserAuthService;

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UploadFormUserEntity uploadFormUserEntity){
        return uploadFormUserAuthService.updateUser(uploadFormUserEntity);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(){
        return uploadFormUserAuthService.deleteUser();
    }
}
