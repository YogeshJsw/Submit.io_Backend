package com.submitIo.requestDto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UploadFormUserEmailSignupRequestDto {

    private String websiteLink;
    private String instituteName;
    private String headInstituteName;
    private String instituteAddress;
    private String instituteContact;
    private String instituteMailId;
    private String loginMailId;
}
