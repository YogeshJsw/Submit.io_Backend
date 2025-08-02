package com.submitIo.repository;

import com.submitIo.entities.ApplyFormUserEmailVerified;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ApplyFormUserVerifiedRepository extends MongoRepository<ApplyFormUserEmailVerified, String> {

    boolean existsByEmail(String email);
}
