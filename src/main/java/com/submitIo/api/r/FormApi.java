package com.submitIo.api.r;

import com.submitIo.entities.FormEntity;
import com.submitIo.requestDto.UpdateFormRequestDto;
import com.submitIo.requestDto.UploadFormRequestDto;
import com.submitIo.service.FormService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/form")
@RequiredArgsConstructor
public class FormApi {

    private final FormService formService;

    @PostMapping("/upload")
    public ResponseEntity<FormEntity> uploadForm(@RequestBody UploadFormRequestDto uploadFormRequestDto){
        return formService.uploadNewForm(uploadFormRequestDto);
    }

    @PutMapping("/update")
    public ResponseEntity<FormEntity> updateForm(@RequestBody UpdateFormRequestDto updateFormRequestDto){
        return formService.updateForm(updateFormRequestDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<FormEntity> deleteForm(@PathVariable("id") String id){
        return formService.deleteForm(id);
    }
}
