package com.submitIo.repository;

import com.submitIo.entities.FormEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FormRepository extends MongoRepository<FormEntity,String> {

    List<FormEntity> findByExamName(String examName);

    List<FormEntity> findByExamHostName(String examHostName);

    List<FormEntity> findByCategory(String category);
}
