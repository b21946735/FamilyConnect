package com.familyconnect.fc.spin;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.ArrayList;

import java.util.List;

import com.familyconnect.fc.family.Family;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Spin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String spinOwner;

    private List<String> spinRewards = new ArrayList<String>();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "family_id")
    private Family family;

    public Spin(String spinOwner, List<String> spinRewards, Family family) {
        super();
        this.spinOwner = spinOwner;
        this.spinRewards = spinRewards;
        this.family = family;
    }

    public Spin() {
        super();
    }

    // Getter ve Setter metotları

    public Integer getId() {
        return id;
    }


    public String getSpinOwner() {
        return spinOwner;
    }

    public void setSpinOwner(String spinOwner) {
        this.spinOwner = spinOwner;
    }

    public List<String> getSpinRewards() {
        return spinRewards;
    }

    public void setSpinRewards(List<String> spinRewards) {
        this.spinRewards = spinRewards;
    }



}
