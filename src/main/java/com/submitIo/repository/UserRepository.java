package com.submitIo.repository;

import com.submitIo.entities.ApplyFormUserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<ApplyFormUserEntity,String> {

    ApplyFormUserEntity findByUsername(String username);

    ApplyFormUserEntity deleteByUsername(String username);
}
