package com.comunidev.comunidevbackend.company.adapter.out.mongodb;

import com.comunidev.comunidevbackend.company.domain.Company;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MongoCompanyRepository extends MongoRepository<Company, String> {
    Optional<Company> findByRecruiterId(String recruiterId);
}
