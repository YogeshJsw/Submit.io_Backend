package com.submitIo.repository;

import com.submitIo.entities.ApplyFormUserEmailUnverified;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface ApplyFormUserUnverifiedRepository extends MongoRepository<ApplyFormUserEmailUnverified, String> {
    
    Optional<ApplyFormUserEmailUnverified> findByEmail(String email);

    boolean existsByEmail(String email);
}
