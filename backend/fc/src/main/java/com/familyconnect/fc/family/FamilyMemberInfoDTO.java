package com.familyconnect.fc.family;

public class FamilyMemberInfoDTO {
    private String userName;
    private String name;
    private int profilePictureId;
    private String role;


    public FamilyMemberInfoDTO(String userName, String name, int profilePictureId, String role) {
        this.userName = userName;
        this.name = name;
        this.profilePictureId = profilePictureId;
        this.role = role;
    }
   

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProfilePictureId() {
        return profilePictureId;
    }

    public void setProfilePictureId(int profilePictureId) {
        this.profilePictureId = profilePictureId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
