package com.submitIo.requestDto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@RequiredArgsConstructor
public class UploadFormRequestDto {

    @Id
    private String id;
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
}
