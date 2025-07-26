package com.submitIo.responseDto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Data
@RequiredArgsConstructor
public class OtpVerificationResponseDto {

    private String email;
    private String otp;
    private Instant otpGeneratedAt;
}
