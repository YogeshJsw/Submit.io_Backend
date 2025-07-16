package com.submitIo.repository;

import com.submitIo.entities.UploadFormUserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UploadFormRepository extends MongoRepository<UploadFormUserEntity,String> {

    UploadFormUserEntity findByUsername(String username);

    UploadFormUserEntity deleteByUsername(String username);
}
