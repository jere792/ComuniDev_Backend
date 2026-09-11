package com.comunidev.comunidevbackend.talent_search.application.port.in;

import com.comunidev.comunidevbackend.talent_search.domain.SavedProfile;

import java.util.List;

public interface SaveProfileUseCase {
    SavedProfile saveProfile(String recruiterId, String developerId, String notes);
    void unsaveProfile(String recruiterId, String developerId);
    boolean isProfileSaved(String recruiterId, String developerId);
    List<SavedProfile> getSavedProfiles(String recruiterId);
}
