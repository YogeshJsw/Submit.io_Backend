package com.submitIo.api.r;

import com.submitIo.entities.UploadFormUserEntity;
import com.submitIo.service.FormService;
import com.submitIo.service.authService.UploadFormUserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/upload")
@RequiredArgsConstructor
public class UploadFormAuthApi {

    private final UploadFormUserAuthService uploadFormUserAuthService;
    private final FormService formService;

    //user-details
    @PutMapping("/user-details/update")
    public ResponseEntity<String> updateUser(@RequestBody UploadFormUserEntity uploadFormUserEntity) {
        return uploadFormUserAuthService.updateUser(uploadFormUserEntity);
    }

    @DeleteMapping("/user-details/delete")
    public ResponseEntity<String> deleteUser() {
        return uploadFormUserAuthService.deleteUser();
    }
}
