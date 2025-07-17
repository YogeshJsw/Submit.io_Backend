package com.submitIo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.submitIo.entities.FormEntity;
import com.submitIo.entities.UploadFormUserEntity;
import com.submitIo.repository.FormRepository;
import com.submitIo.repository.UploadFormRepository;
import com.submitIo.requestDto.UpdateFormRequestDto;
import com.submitIo.requestDto.UploadFormRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FormService {

    private final FormRepository formRepository;
    private final ObjectMapper objectMapper;
    private final UploadFormRepository uploadFormRepository;


    public ResponseEntity<FormEntity> uploadNewForm(UploadFormRequestDto uploadFormRequestDto) {
//        Authentication authenticatedUser = SecurityContextHolder.getContext().getAuthentication();
//        String authenticatedUserName = authenticatedUser.getName();
//        UploadFormUserEntity uploadFormUserEntity = uploadFormRepository.findByUsername(authenticatedUserName);
//        if(uploadFormUserEntity == null || uploadFormUserEntity.getRoles().contains("USER")){
//            log.error("User: {} does not have required permission."+authenticatedUserName);
//            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
        FormEntity formEntity = objectMapper.convertValue(uploadFormRequestDto, FormEntity.class);
        FormEntity savedForm = formRepository.save(formEntity);
        if(savedForm.getId()==null)
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

    public ResponseEntity<FormEntity> updateForm(UpdateFormRequestDto updateFormRequestDto) {
        FormEntity formEntity=formRepository.findById(updateFormRequestDto.getId()).orElse(null);
        if(formEntity==null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if(updateFormRequestDto.getCategory()!=null)
            formEntity.setCategory(updateFormRequestDto.getCategory());
        if(updateFormRequestDto.getDeadline()!=null)
            formEntity.setDeadline(updateFormRequestDto.getDeadline());
        if(updateFormRequestDto.getEligibility()!=null)
            formEntity.setEligibility(updateFormRequestDto.getEligibility());
        if(updateFormRequestDto.getExamDate()!=null)
            formEntity.setExamDate(updateFormRequestDto.getExamDate());
        if(updateFormRequestDto.getExamDescription()!=null)
            formEntity.setExamDescription(updateFormRequestDto.getExamDescription());
        if(updateFormRequestDto.getExamHostName()!=null)
            formEntity.setExamHostName(updateFormRequestDto.getExamHostName());
        if(updateFormRequestDto.getExamName()!=null)
            formEntity.setExamName(updateFormRequestDto.getExamName());
        if(updateFormRequestDto.getFees()!=0)
            formEntity.setFees(updateFormRequestDto.getFees());
        if(updateFormRequestDto.getExamIcon()!=null)
            formEntity.setExamIcon(updateFormRequestDto.getExamIcon());
        if(updateFormRequestDto.getLink()!=null)
            formEntity.setLink(updateFormRequestDto.getLink());
        if(updateFormRequestDto.getStatus()!=null)
            formEntity.setStatus(updateFormRequestDto.getStatus());
        formRepository.save(formEntity);
        return new ResponseEntity<>(formEntity,HttpStatus.OK);
    }

    public ResponseEntity<FormEntity> deleteForm(String id) {
        if(!formRepository.existsById(id))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        formRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
