package com.submitIo.responseDto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UploadFormResponseDto {

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
