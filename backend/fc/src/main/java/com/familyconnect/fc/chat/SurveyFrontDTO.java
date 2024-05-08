package com.familyconnect.fc.chat;
import java.util.*;

public class SurveyFrontDTO {
    private String option;
    private List<String> voters;

    public SurveyFrontDTO(String option, List<String> voters) {
        super();
        this.option = option;
        this.voters = voters;
    }

    public SurveyFrontDTO() {
        super();
    }

    public String getOption() {
        return option;
    }



    public void setOption(String option) {
        this.option = option;
    }

    public List<String> getVoters() {
        return voters;
    }

    public void setVoters(List<String> voters) {
        this.voters = voters;
    }
}
