package com.submitIo.api.u;

import com.submitIo.entities.FormEntity;
import com.submitIo.entities.UploadFormUserEntity;
import com.submitIo.entities.ApplyFormUserEntity;
import com.submitIo.service.UploadFormUserAuthService;
import com.submitIo.service.ApplyFormUserAuthService;
import com.submitIo.service.FormService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/u")
@RequiredArgsConstructor
public class PublicController {

    private final ApplyFormUserAuthService applyFormUserAuthService;
    private final FormService formService;
    private final UploadFormUserAuthService uploadFormUserAuthService;

    @PostMapping("/create/user/upload-form")
    public ResponseEntity<String> createUploadFormUser(@RequestBody UploadFormUserEntity uploadFormUserEntity){
        return uploadFormUserAuthService.createUser(uploadFormUserEntity);
    }

    @PostMapping("/create/user/apply-form")
    public ResponseEntity<ApplyFormUserEntity> createApplyFormUser(@RequestBody ApplyFormUserEntity applyFormUserEntity){
        return applyFormUserAuthService.createUser(applyFormUserEntity);
    }

    @GetMapping("/all")
    public ResponseEntity<List<FormEntity>> allForms(){
        return formService.getAllForms();
    }

    @GetMapping("/examName/{examName}")
    public ResponseEntity<List<FormEntity>> formByName(@PathVariable String examName){
        return formService.getFormByExamName(examName);
    }

    @GetMapping("/hostName/{examHostName}")
    public ResponseEntity<List<FormEntity>> formByHostName(@PathVariable String examHostName){
        return formService.getFormByExamHostName(examHostName);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<FormEntity>> formByCategory(@PathVariable String category){
        return formService.getFormByCategory(category);
    }
}
