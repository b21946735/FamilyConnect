package com.familyconnect.fc.chat;

import com.familyconnect.fc.family.Family;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String senderUsername;
    private String senderName;
    private String message;
    private String timestamp;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "family_id")
    private Family family;

    public ChatMessage(String senderUsername,String senderName , String message, String timestamp, Family family) {
        super();
        this.senderUsername = senderUsername;
        this.senderName = senderName;
        this.family = family;
        this.message = message;
        this.timestamp = timestamp;
    }

    public ChatMessage() {
        super();
    }

    // Getter ve Setter metotları
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String sender) {
        this.senderUsername = sender;
    }

    public Family getFamily() {
        return family;
    }

    public void setFamily(Family receiverFamily) {
        this.family = receiverFamily;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
