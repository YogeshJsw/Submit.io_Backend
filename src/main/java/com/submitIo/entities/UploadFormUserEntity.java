package com.submitIo.entities;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
@Document(collection = "uploadFormUsers")
public class UploadFormUserEntity {

    @Id
    private String id;
    @Indexed(unique = true)
    private String username;
    private String email;
    private String password;
    private List<FormEntity> uploadedForms=new ArrayList<>();
    private List<String> roles;
}
