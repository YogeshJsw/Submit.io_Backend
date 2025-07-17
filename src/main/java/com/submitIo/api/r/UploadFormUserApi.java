package com.submitIo.api.r;

import com.submitIo.entities.UploadFormUserEntity;
import com.submitIo.service.UploadFormUserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/upload")
@RequiredArgsConstructor
public class UploadFormUserApi {

    private final UploadFormUserAuthService uploadFormUserAuthService;

    @PutMapping("/user-details/update")
    public ResponseEntity<?> updateUser(@RequestBody UploadFormUserEntity uploadFormUserEntity){
        return uploadFormUserAuthService.updateUser(uploadFormUserEntity);
    }

    @DeleteMapping("/user-details/delete")
    public ResponseEntity<?> deleteUser(){
        return uploadFormUserAuthService.deleteUser();
    }
}
