package com.submitIo.requestDto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class EmailLoginRequestDto {

    private String email;
    private String password;
}
