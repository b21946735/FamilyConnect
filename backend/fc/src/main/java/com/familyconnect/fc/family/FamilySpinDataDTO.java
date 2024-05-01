package com.familyconnect.fc.family;

import java.util.List;

public class FamilySpinDataDTO {

    private Integer id;
    private String username;
    private String prize;

    public FamilySpinDataDTO(Integer id,String username,  String prize) {
        super();
        this.id = id;
        this.username = username;
        this.prize = prize;
    }

    public FamilySpinDataDTO() {
        super();
    }

    // Getter ve Setter metotları

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }
    

    
}
