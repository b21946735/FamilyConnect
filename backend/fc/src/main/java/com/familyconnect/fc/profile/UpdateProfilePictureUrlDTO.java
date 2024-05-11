package com.familyconnect.fc.profile;

public class UpdateProfilePictureUrlDTO {
    private String username;
    private String profilePictureUrl;

    public UpdateProfilePictureUrlDTO(String username, String profilePictureUrl) {
        this.username = username;
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }
}
