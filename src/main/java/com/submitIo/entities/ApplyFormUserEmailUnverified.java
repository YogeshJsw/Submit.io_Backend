package com.submitIo.entities;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "applyFormUsersUnverified")
@Data
@RequiredArgsConstructor
public class ApplyFormUserEmailUnverified {

    @Id
    private String id;
    private String username;
    @Indexed(unique = true)
    private String email;
    private String password;
    private boolean isVerified=false;
    private List<FormEntity> appliedForms=new ArrayList<>();
    private List<String> roles;
    private String otp;
    private Instant otpGeneratedAt;
}