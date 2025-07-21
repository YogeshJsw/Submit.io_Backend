package com.submitIo.requestDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApplyFormUserEmailSignupRequestDto {

    private String username;
    private String email;
    private String password;
}
