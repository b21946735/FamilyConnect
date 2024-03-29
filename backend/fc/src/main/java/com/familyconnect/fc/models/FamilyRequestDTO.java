package com.familyconnect.fc.models;

public class FamilyRequestDTO {
    private String familyName;
    private String familyCreatorUserName;


    public FamilyRequestDTO(){
        super();
    }

    public FamilyRequestDTO(String familyName, String familyCreatorUserName){
        super();
        this.familyName = familyName;
        this.familyCreatorUserName = familyCreatorUserName;

    }
    
    public String getFamilyName(){
        return this.familyName;
    }

    public void setFamilyName(String familyName){
        this.familyName = familyName;
    }

    public String getFamilyCreatorUserName(){
        return this.familyCreatorUserName;
    }

    public void setFamilyCreatorUserName(String familyCreatorUserName){
        this.familyCreatorUserName = familyCreatorUserName;
    }

    public String toString(){
        return "Registration info: username: " + this.familyName;
    }
}