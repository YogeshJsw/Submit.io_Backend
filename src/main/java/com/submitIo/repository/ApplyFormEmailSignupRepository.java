package com.submitIo.repository;

import com.submitIo.entities.ApplyFormUserEmailSignupEntity;
import com.submitIo.entities.ApplyFormUserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ApplyFormEmailSignupRepository extends MongoRepository<ApplyFormUserEmailSignupEntity,String> {

    ApplyFormUserEmailSignupEntity findByUsername(String username);

    ApplyFormUserEmailSignupEntity deleteByUsername(String username);
}

