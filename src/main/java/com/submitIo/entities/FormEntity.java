package com.submitIo.entities;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "forms")
@Data
@RequiredArgsConstructor
public class FormEntity {

    @Id
    private String formId;
    private String userId;
    private String category;
    private String deadline;
    private String eligibility;
    private String examDate;
    private String examDescription;
    private String examHostName;
    private String examName;
    private int fees;
    private String examIcon;
    private String link;
    private String status;
    private String uploadedBy;
}