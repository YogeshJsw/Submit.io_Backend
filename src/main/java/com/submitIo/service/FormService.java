package com.submitIo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.submitIo.entities.FormEntity;
import com.submitIo.entities.UploadFormUserEntity;
import com.submitIo.repository.FormRepository;
import com.submitIo.repository.UploadFormRepository;
import com.submitIo.requestDto.UpdateFormRequestDto;
import com.submitIo.requestDto.UploadFormRequestDto;
import com.submitIo.responseDto.UploadFormResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FormService {

    private final FormRepository formRepository;
    private final ObjectMapper objectMapper;
    private final UploadFormRepository uploadFormRepository;


    public ResponseEntity<UploadFormResponseDto> uploadNewForm(UploadFormRequestDto uploadFormRequestDto) {
        FormEntity formEntity = objectMapper.convertValue(uploadFormRequestDto, FormEntity.class);
        formEntity.setUploadedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        FormEntity savedForm = formRepository.save(formEntity);
        UploadFormResponseDto uploadFormResponseDto = objectMapper.convertValue(formEntity, UploadFormResponseDto.class);
        if(savedForm.getId()==null)
            return new ResponseEntity<>(uploadFormResponseDto, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(uploadFormResponseDto,HttpStatus.CREATED);
    }

    public ResponseEntity<List<UploadFormResponseDto>> getAllForms() {
        List<FormEntity> formEntities = formRepository.findAll();
        List<UploadFormResponseDto> uploadFormResponseDtos = new ArrayList<>();
        for(FormEntity formEntity:formEntities){
            uploadFormResponseDtos.add(objectMapper.convertValue(formEntity, UploadFormResponseDto.class));
        }
        return new ResponseEntity<>(uploadFormResponseDtos,HttpStatus.OK);
    }

    public ResponseEntity<List<UploadFormResponseDto>> getFormByExamName(String examName) {
        List<FormEntity> formEntities = formRepository.findByExamName(examName);
        List<UploadFormResponseDto> uploadFormResponseDtos = new ArrayList<>();
        for(FormEntity formEntity:formEntities){
            uploadFormResponseDtos.add(objectMapper.convertValue(formEntity, UploadFormResponseDto.class));
        }
        return new ResponseEntity<>(uploadFormResponseDtos,HttpStatus.OK);
    }

    public ResponseEntity<List<UploadFormResponseDto>> getFormByExamHostName(String examHostName) {
        List<FormEntity> formEntities = formRepository.findByExamHostName(examHostName);
        List<UploadFormResponseDto> uploadFormResponseDtos = new ArrayList<>();
        for(FormEntity formEntity:formEntities){
            uploadFormResponseDtos.add(objectMapper.convertValue(formEntity, UploadFormResponseDto.class));
        }
        return new ResponseEntity<>(uploadFormResponseDtos,HttpStatus.OK);
    }

    public ResponseEntity<List<UploadFormResponseDto>> getFormByCategory(String category) {
        List<FormEntity> formEntities = formRepository.findByCategory(category);
        List<UploadFormResponseDto> uploadFormResponseDtos = new ArrayList<>();
        for(FormEntity formEntity:formEntities){
            uploadFormResponseDtos.add(objectMapper.convertValue(formEntity, UploadFormResponseDto.class));
        }
        return new ResponseEntity<>(uploadFormResponseDtos,HttpStatus.OK);
    }

    public ResponseEntity<UploadFormResponseDto> updateForm(UpdateFormRequestDto updateFormRequestDto) {
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
        UploadFormResponseDto uploadFormResponseDto = objectMapper.convertValue(formEntity, UploadFormResponseDto.class);
        return new ResponseEntity<>(uploadFormResponseDto,HttpStatus.OK);
    }

    public ResponseEntity<String> deleteForm(String id) {
        if(!formRepository.existsById(id))
            return new ResponseEntity<>("Form not found with id: "+id,HttpStatus.BAD_REQUEST);
        formRepository.deleteById(id);
        return new ResponseEntity<>("Form deleted",HttpStatus.OK);
    }
}
