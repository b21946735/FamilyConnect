package com.familyconnect.fc.family;

import java.util.ArrayList;

import java.util.List;


public class FamilyPhotosDTO {
    private List<String> familyPhotos = new ArrayList<String>();
    private String username;

    public FamilyPhotosDTO(String username, List<String> familyPhotos) {
        this.username = username;
        this.familyPhotos = familyPhotos;
    }

    public FamilyPhotosDTO(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    



    public List<String> getFamilyPhotos() {
        return familyPhotos;
    }

    public void setFamilyPhotos(List<String> familyPhotos) {
        this.familyPhotos = familyPhotos;
    }

    public void addFamilyPhoto(String familyPhoto) {
        this.familyPhotos.add(familyPhoto);
    }

    public void removeFamilyPhoto(String familyPhoto) {
        this.familyPhotos.remove(familyPhoto);
    }

    public void clearFamilyPhotos() {
        this.familyPhotos.clear();
    }

    public boolean containsFamilyPhoto(String familyPhoto) {
        return this.familyPhotos.contains(familyPhoto);
    }

    public int size() {
        return this.familyPhotos.size();
    }
}
