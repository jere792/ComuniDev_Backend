package com.comunidev.comunidevbackend.vacancy.adapter.out.mongodb;

import com.comunidev.comunidevbackend.vacancy.application.port.out.VacancyRepositoryPort;
import com.comunidev.comunidevbackend.vacancy.domain.Vacancy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MongoVacancyAdapter implements VacancyRepositoryPort {

    private final MongoVacancyRepository repository;

    @Override
    public Vacancy save(Vacancy vacancy) {
        return repository.save(vacancy);
    }

    @Override
    public Optional<Vacancy> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<Vacancy> findAllByEstado(String estado) {
        return repository.findAllByEstado(estado);
    }

    @Override
    public List<Vacancy> findAllByCompanyId(String companyId) {
        return repository.findAllByCompanyId(companyId);
    }

    @Override
    public List<Vacancy> findAllByRecruiterId(String recruiterId) {
        return repository.findAllByRecruiterId(recruiterId);
    }

    @Override
    public int countByCompanyIdAndEstado(String companyId, String estado) {
        return repository.countByCompanyIdAndEstado(companyId, estado);
    }
}
