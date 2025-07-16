package com.submitIo.entities;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
@Document(collection = "users")
public class ApplyFormUserEntity {

    @Id
    private String id;

    @Indexed(unique = true)
    private String username;
    private String email;
    private String password;
//    @DBRef
    private List<FormEntity> appliedForms=new ArrayList<>();
    private List<String> roles;

}
