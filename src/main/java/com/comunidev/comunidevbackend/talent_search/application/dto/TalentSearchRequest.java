package com.comunidev.comunidevbackend.talent_search.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TalentSearchRequest {
    private List<String> technologies;
    private String experienceLevel;
    private String locationCountry;
    private String locationCity;
    private Boolean remoteAvailable;
    private String availability;
    private List<String> previousExperience;
    private String sortBy;
    private String sortOrder;
    private Integer page;
    private Integer size;
}
