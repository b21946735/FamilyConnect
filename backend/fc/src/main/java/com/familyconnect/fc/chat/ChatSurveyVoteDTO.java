package com.familyconnect.fc.chat;

public class ChatSurveyVoteDTO {
    private Integer surveyId;
    private String username;
    private Integer option;


    public ChatSurveyVoteDTO(Integer surveyId, String username, Integer option) {
        super();
        this.surveyId = surveyId;
        this.username = username;
        this.option = option;
    }

    public ChatSurveyVoteDTO() {
        super();
    }

    public Integer getSurveyId() {

        return surveyId;
    }

    public void setSurveyId(Integer surveyId) {
        this.surveyId = surveyId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getOption() {
        return option;
    }

    public void setOption(Integer option) {
        this.option = option;
    }




}
