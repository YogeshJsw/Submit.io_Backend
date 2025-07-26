package com.submitIo.entities;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "applyFormUsersVerified")
@Data
@RequiredArgsConstructor
public class ApplyFormUserEmailVerified {
    @Id
    private String id;
    private String username;
    @Indexed(unique = true)
    private String email;
    private String password;
    private List<String> roles;
    private List<FormEntity> appliedForms = new ArrayList<>();
}

