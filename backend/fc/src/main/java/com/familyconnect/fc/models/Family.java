package com.familyconnect.fc.models;



import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
public class Family {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String familyName;
    private List<String> familyMembers = new ArrayList<String>();
  
    public Family(String familyName, String creatorUserName) {
		super();
		this.familyName = familyName;
        this.familyMembers.add(creatorUserName);
	}

    public Family() {
        super();
    }

    // Getter ve Setter metotları
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFamilyName() { 
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }
}
