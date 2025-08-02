package com.submitIo.api.r;

import com.submitIo.entities.FormEntity;
import com.submitIo.responseDto.UploadFormResponseDto;
import com.submitIo.service.FormService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/query/form")
@RequiredArgsConstructor
public class FormQueryApi {

    private final FormService formService;

    //forms
    @GetMapping("/all")
    public ResponseEntity<List<UploadFormResponseDto>> allForms(){
        return formService.getAllForms();
    }

    @GetMapping("/examName/{examName}")
    public ResponseEntity<List<UploadFormResponseDto>> formByName(@PathVariable String examName){
        return formService.getFormByExamName(examName);
    }

    @GetMapping("/hostName/{examHostName}")
    public ResponseEntity<List<UploadFormResponseDto>> formByHostName(@PathVariable String examHostName){
        return formService.getFormByExamHostName(examHostName);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<UploadFormResponseDto>> formByCategory(@PathVariable String category){
        return formService.getFormByCategory(category);
    }
}
