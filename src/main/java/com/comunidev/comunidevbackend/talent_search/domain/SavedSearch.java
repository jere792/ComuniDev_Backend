package com.comunidev.comunidevbackend.talent_search.domain;

import com.comunidev.comunidevbackend.shared.domain.AggregateRoot;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Document(collection = "saved_searches")
public class SavedSearch extends AggregateRoot<String> {
    private String recruiterId;
    private String name;
    private List<String> technologies;
    private String experienceLevel;
    private String locationCountry;
    private String locationCity;
    private Boolean remoteAvailable;
    private String availability;
    private List<String> previousExperience;
    private Instant createdAt;

    public static SavedSearch create(String recruiterId, String name, List<String> technologies,
                                      String experienceLevel, String locationCountry, String locationCity,
                                      Boolean remoteAvailable, String availability, List<String> previousExperience) {
        SavedSearch search = new SavedSearch();
        search.setRecruiterId(recruiterId);
        search.setName(name);
        search.setTechnologies(technologies);
        search.setExperienceLevel(experienceLevel);
        search.setLocationCountry(locationCountry);
        search.setLocationCity(locationCity);
        search.setRemoteAvailable(remoteAvailable);
        search.setAvailability(availability);
        search.setPreviousExperience(previousExperience);
        search.setCreatedAt(Instant.now());
        return search;
    }
}
