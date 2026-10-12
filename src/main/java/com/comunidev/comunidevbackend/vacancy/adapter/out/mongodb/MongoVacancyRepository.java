package com.comunidev.comunidevbackend.vacancy.adapter.out.mongodb;

import com.comunidev.comunidevbackend.vacancy.domain.Vacancy;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoVacancyRepository extends MongoRepository<Vacancy, String> {
    List<Vacancy> findAllByEstado(String estado);
    List<Vacancy> findAllByCompanyId(String companyId);
    List<Vacancy> findAllByRecruiterId(String recruiterId);
    int countByCompanyIdAndEstado(String companyId, String estado);
}
