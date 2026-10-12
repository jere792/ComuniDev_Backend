package com.comunidev.comunidevbackend.company.adapter.out.mongodb;

import com.comunidev.comunidevbackend.company.application.port.out.CompanyRepositoryPort;
import com.comunidev.comunidevbackend.company.domain.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MongoCompanyAdapter implements CompanyRepositoryPort {

    private final MongoCompanyRepository repository;

    @Override
    public Company save(Company company) {
        return repository.save(company);
    }

    @Override
    public Optional<Company> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<Company> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Company> findByRecruiterId(String recruiterId) {
        return repository.findByRecruiterId(recruiterId);
    }

    @Override
    public void setVacantesActivasCount(String companyId, int count) {
        repository.findById(companyId).ifPresent(company -> {
            company.setVacantesActivasCount(count);
            repository.save(company);
        });
    }
}
