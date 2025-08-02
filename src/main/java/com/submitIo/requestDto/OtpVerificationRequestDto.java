package com.submitIo.requestDto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class OtpVerificationRequestDto {

    private String email;
    private String otp;
}
