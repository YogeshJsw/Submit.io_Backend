package com.submitIo.requestDto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class PhoneLoginRequestDto {

    private String phoneNumber;
}
