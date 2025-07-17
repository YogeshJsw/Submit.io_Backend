package com.submitIo.api.r;

import com.submitIo.entities.ApplyFormUserEntity;
import com.submitIo.entities.FormEntity;
import com.submitIo.service.ApplyFormUserAuthService;
import com.submitIo.service.FormService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apply")
@RequiredArgsConstructor
public class ApplyFormUserApi {

    private final FormService formService;

    @GetMapping("/form/all")
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