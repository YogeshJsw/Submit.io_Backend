package com.submitIo.api.r;

import com.submitIo.entities.FormEntity;
import com.submitIo.requestDto.UploadFormRequestDto;
import com.submitIo.service.FormService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/r/form")
@RequiredArgsConstructor
public class FormController {

    private final FormService formService;

    @PostMapping("/upload")
    public ResponseEntity<FormEntity> uploadForm(@RequestBody UploadFormRequestDto uploadFormRequestDto){
        return formService.uploadNewForm(uploadFormRequestDto);
    }

//    @PostMapping("/update")
//    public ResponseEntity<?> updateForm(@RequestBody UploadFormRequestDto uploadFormRequestDto){
//
//    }
}
