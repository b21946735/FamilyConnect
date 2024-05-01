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
public class Reward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String rewardOwner;

    private String reward;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "family_id")
    private Family family;

    public Reward(String rewardOwner, String reward, Family family) {
        super();
        this.rewardOwner = rewardOwner;
        this.reward = reward;
        this.family = family;
    }

    public Reward() {
        super();
    }

    // Getter ve Setter metotları

    public Integer getId() {
        return id;
    }


    public String getRewardOwner() {
        return rewardOwner;
    }

    public void setRewardOwner(String rewardOwner) {
        this.rewardOwner = rewardOwner;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    



}
