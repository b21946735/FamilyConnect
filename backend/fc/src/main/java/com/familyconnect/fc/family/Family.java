package com.familyconnect.fc.family;



import java.util.ArrayList;
import java.util.List;

import com.familyconnect.fc.chat.ChatMessage;
import com.familyconnect.fc.chat.ChatSurvey;
import com.familyconnect.fc.spin.Reward;
import com.familyconnect.fc.spin.Spin;
import com.familyconnect.fc.task.Task;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Family {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String familyName;
    private List<String> familyMembers = new ArrayList<String>();
    private String creatorUserName;

    @JsonIgnore
    @OneToMany(mappedBy = "family", cascade = CascadeType.ALL)
    private List<Task> tasks = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "family", cascade = CascadeType.ALL)
    private List<Spin> spins = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "family", cascade = CascadeType.ALL)
    private List<Reward> earnedRewards = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "family", cascade = CascadeType.ALL)
    private List<ChatMessage> chatMessages = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "family", cascade = CascadeType.ALL)
    private List<ChatSurvey> chatSurveys = new ArrayList<>();

    private List<String> familyPhotos = new ArrayList<String>();

  
    public Family(String familyName, String creatorUserName) {
		super();
		this.familyName = familyName;
        this.creatorUserName = creatorUserName;
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

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<String> getFamilyMembers() {
        return familyMembers;
    }

    public void setFamilyMembers(List<String> familyMembers) {
        this.familyMembers = familyMembers;
    }

    public String getCreatorUserName() {
        return creatorUserName;
    }

    public void setCreatorUserName(String creatorUserName) {
        this.creatorUserName = creatorUserName;
    }

    public void addTask(Task task) {
        System.out.println("Task added to family " + this.familyName + " tasks size from " + this.tasks.size() + " to " + (this.tasks.size() + 1));
        this.tasks.add(task);
    }

    public void removeTask(Task task) {
        this.tasks.remove(task);
    }


    // getter and setter for spins
    public List<Spin> getSpins() {
        return spins;
    }

    public void setSpins(List<Spin> spins) {
        this.spins = spins;
    }

    public void addSpin(Spin spin) {
        System.out.println("Adding spin for user " + spin.getSpinOwner() + " rewards: " + spin.getSpinRewards().toString());
        spins.add(spin);
    }

    public boolean removeSpin(Integer spinId) {
        for (Spin spin : spins) {
            if (spin.getId() == spinId) {
                spins.remove(spin);
                System.out.println("Spin removed from family spins. Spin id: " + spinId);
                return true;
            }
        }

        System.out.println("Spin not found in family spins. Spin id: " + spinId);

        return false;
    }

    public List<Spin> getUserSpins(String userName) {
        List<Spin> userSpins = new ArrayList<>();

        for (Spin spin : spins) {
            if (spin.getSpinOwner().equals(userName)) {
                userSpins.add(spin);
            }
        }

        return userSpins;
    }

    // getter and setter for earned rewards
    public List<Reward> getEarnedRewards() {
        return earnedRewards;
    }

    public void setEarnedRewards(List<Reward> earnedRewards) {
        this.earnedRewards = earnedRewards;
    }

    public void addEarnedReward(Reward reward) {
        earnedRewards.add(reward);
    }

    public void removeEarnedReward(Reward reward) {
        earnedRewards.remove(reward);
    }


    public void PrintSpins() {
        for (Spin spin : spins) {
            System.out.println("Spin owner: " + spin.getSpinOwner() + " rewards: " + spin.getSpinRewards().toString());
        }

        if (spins.size() == 0) {
            System.out.println("No spins available");
        }
    }

    public List<Reward> getUserRewards(String username) {
        List<Reward> userRewards = new ArrayList<>();

        for (Reward reward : earnedRewards) {
            if (reward.getRewardOwner().equals(username)) {
                userRewards.add(reward);
            }
        }

        return userRewards;
    }

    public void AddChatMessage(ChatMessage chatMessage) {
        chatMessages.add(chatMessage);
    }

    public List<ChatMessage> getChatMessages() {
        return chatMessages;
    }

    public void setChatMessages(List<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }

    public void PrintChatMessages() {
        for (ChatMessage chatMessage : chatMessages) {
            System.out.println("Sender: " + chatMessage.getSenderUsername() + " Message: " + chatMessage.getMessage());
        }

        if (chatMessages.size() == 0) {
            System.out.println("No chat messages available");
        }
    }

    public void AddChatSurvey(ChatSurvey chatSurvey) {
        chatSurveys.add(chatSurvey);
    }

    public List<ChatSurvey> getChatSurveys() {
        return chatSurveys;
    }

    public void setChatSurveys(List<ChatSurvey> chatSurveys) {
        this.chatSurveys = chatSurveys;
    }

    public void PrintChatSurveys() {
        for (ChatSurvey chatSurvey : chatSurveys) {
            System.out.println("Sender: " + chatSurvey.getSenderName() + " Survey: " + chatSurvey.getSurvey().toString());
        }

        if (chatSurveys.size() == 0) {
            System.out.println("No chat surveys available");
        }
    }

    public List<String> getFamilyPhotos() {
        return familyPhotos;
    }

    public void setFamilyPhotos(List<String> familyPhotos) {
        this.familyPhotos = familyPhotos;
    }

    public void addFamilyPhoto(String photo) {
        familyPhotos.add(photo);
    }

    public void removeFamilyPhoto(String photo) {
        familyPhotos.remove(photo);
    }
    
}
