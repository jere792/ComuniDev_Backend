package com.comunidev.comunidevbackend.talent_search.domain;

import com.comunidev.comunidevbackend.shared.domain.AggregateRoot;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Setter
@Document(collection = "saved_profiles")
public class SavedProfile extends AggregateRoot<String> {
    private String recruiterId;
    private String developerId;
    private String notes;
    private Instant savedAt;

    public static SavedProfile create(String recruiterId, String developerId, String notes) {
        SavedProfile profile = new SavedProfile();
        profile.setRecruiterId(recruiterId);
        profile.setDeveloperId(developerId);
        profile.setNotes(notes);
        profile.setSavedAt(Instant.now());
        return profile;
    }
}
