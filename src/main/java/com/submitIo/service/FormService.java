package com.submitIo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.submitIo.entities.FormEntity;
import com.submitIo.repository.FormRepository;
import com.submitIo.requestDto.UploadFormRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FormService {

    private final FormRepository formRepository;
    private final ObjectMapper objectMapper;


    public ResponseEntity<FormEntity> uploadNewForm(UploadFormRequestDto uploadFormRequestDto) {
        FormEntity formEntity = objectMapper.convertValue(uploadFormRequestDto, FormEntity.class);
        FormEntity savedForm = formRepository.save(formEntity);
        if(savedForm.getFormId()==null)
            return new ResponseEntity<>(savedForm, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(savedForm,HttpStatus.CREATED);
    }

    public ResponseEntity<List<FormEntity>> getAllForms() {
        return new ResponseEntity<>(formRepository.findAll(),HttpStatus.OK);
    }

    public ResponseEntity<List<FormEntity>> getFormByExamName(String examName) {
        return new ResponseEntity<>(formRepository.findByExamName(examName),HttpStatus.OK);
    }

    public ResponseEntity<List<FormEntity>> getFormByExamHostName(String examHostName) {
        return new ResponseEntity<>(formRepository.findByExamHostName(examHostName),HttpStatus.OK);
    }

    public ResponseEntity<List<FormEntity>> getFormByCategory(String category) {
        return new ResponseEntity<>(formRepository.findByCategory(category),HttpStatus.OK);
    }
}
