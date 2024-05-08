package com.familyconnect.fc.chat;
import java.util.*; 




public class ChatBaseMessageDTO {
    private Integer id;
    private String type;
    private String senderUsername;
    private String senderName;
    private String message;
    private String description;
    private List<SurveyFrontDTO> surveyVotes = new ArrayList<SurveyFrontDTO>();

    private String timestamp;


    public ChatBaseMessageDTO(Integer id,String type, String senderUsername, String senderName, String message,  String timestamp, String description, List<String> survey,Map<String, Integer> surveyResults) {
        super();
        this.id = id;
        this.type = type;
        this.senderUsername = senderUsername;
        this.senderName = senderName;
        this.timestamp = timestamp;
        this.description = description;
        this.message = message;
        if(type.equals("survey")){
            SetSurveyResults(surveyResults, survey);
        }
    }

    public void SetSurveyResults(Map<String, Integer> surveyResults, List<String> survey) {
        for (String option : survey) {
            List<String> voters = new ArrayList<String>();
            for (Map.Entry<String, Integer> entry : surveyResults.entrySet()) {
                if (entry.getValue() == survey.indexOf(option)) {
                    voters.add(entry.getKey());
                }
            }
            SurveyFrontDTO surveyFrontDTO = new SurveyFrontDTO(option, voters);
            surveyVotes.add(surveyFrontDTO);
        }
    }

    public ChatBaseMessageDTO() {
        super();
    }

    // Getter ve Setter metotları

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String sender) {
        this.senderUsername = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SurveyFrontDTO> getSurveyVotes() {
        return surveyVotes;
    }

    public void setSurveyVotes(List<SurveyFrontDTO> surveyVotes) {
        this.surveyVotes = surveyVotes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


}
