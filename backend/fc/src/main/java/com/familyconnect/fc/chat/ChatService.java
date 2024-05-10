package com.familyconnect.fc.chat;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.familyconnect.fc.family.Family;
import com.familyconnect.fc.family.FamilyRepository;
import com.familyconnect.fc.user.ApplicationUser;
import com.familyconnect.fc.user.UserRepository;

import java.util.List;
import java.util.ArrayList;


@Service
@Transactional
public class ChatService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FamilyRepository familyRepository;

    @Autowired
    private ChatSurveyRepository chatSurveyRepository;

    public ResponseEntity<?> sendMessage(ChatMessageDTO chatMessageDTO) {
        String sender = chatMessageDTO.getSender();
        String message = chatMessageDTO.getMessage();
        String timestamp = chatMessageDTO.getTimestamp();


        // check if sender exists and is a member of the family
        if (!userRepository.findByUsername(sender).isPresent()) {
            return ResponseEntity.badRequest().body("Sender does not exist");
        }

        ApplicationUser senderUser = userRepository.findByUsername(sender).get();
        
        int receiverFamilyId = senderUser.getFamilyId();

        if (!familyRepository.findById(receiverFamilyId).isPresent()) {
            return ResponseEntity.badRequest().body("Family does not exist");
        }

        Family receiverFamily = familyRepository.findById(receiverFamilyId).get();

        ChatMessage chatMessage = new ChatMessage(sender, senderUser.getName(), message, timestamp, receiverFamily);

        receiverFamily.AddChatMessage(chatMessage);
        
        return ResponseEntity.ok(chatMessageDTO);
    }

    public ResponseEntity<?> sendSurvey(ChatSurveyDTO chatSurveyDTO) {
        String sender = chatSurveyDTO.getSender();
        String description = chatSurveyDTO.getDescription();
        List<String> survey = chatSurveyDTO.getSurvey();
        String timestamp = chatSurveyDTO.getTimestamp();

        // check if sender exists and is a member of the family
        if (!userRepository.findByUsername(sender).isPresent()) {
            return ResponseEntity.badRequest().body("Sender does not exist");
        }

        ApplicationUser senderUser = userRepository.findByUsername(sender).get();
        
        int receiverFamilyId = senderUser.getFamilyId();

        if (!familyRepository.findById(receiverFamilyId).isPresent()) {
            return ResponseEntity.badRequest().body("Family does not exist");
        }

        Family receiverFamily = familyRepository.findById(receiverFamilyId).get();

        ChatSurvey chatSurvey = new ChatSurvey(sender, senderUser.getName(), survey, timestamp, receiverFamily, description);

        receiverFamily.AddChatSurvey(chatSurvey);
        
        return ResponseEntity.ok(chatSurveyDTO);
    }

    public ResponseEntity<?> getMessages(String username) {
        if (!userRepository.findByUsername(username).isPresent()) {
            return ResponseEntity.badRequest().body("User does not exist");
        }

        ApplicationUser user = userRepository.findByUsername(username).get();
        int familyId = user.getFamilyId();

        if (!familyRepository.findById(familyId).isPresent()) {
            return ResponseEntity.badRequest().body("Family does not exist");
        }

        Family family = familyRepository.findById(familyId).get();

        // get all messages and surveys

        List<ChatMessage> messages = family.getChatMessages();

        List<ChatSurvey> surveys = family.getChatSurveys();

        List<ChatBaseMessageDTO> chatMessages = new ArrayList<>();

        for (ChatMessage message : messages) {
            ChatBaseMessageDTO chatMessageDTO = new ChatBaseMessageDTO(message.getId(),"message",message.getSenderUsername(), message.getSenderName(),
                                                     message.getMessage(), message.getTimestamp(),"",null,null);
            chatMessages.add(chatMessageDTO);
        }

        for (ChatSurvey survey : surveys) {
            ChatBaseMessageDTO chatSurveyDTO = new ChatBaseMessageDTO(survey.getId(),"survey",survey.getSenderUsername(), survey.getSenderName(),
                                                     "", survey.getTimestamp(), survey.getDescription(), survey.getSurvey(), survey.getSurveyResults());
            chatMessages.add(chatSurveyDTO);
        }

        // sort messages by timestamp from oldest to newest
        chatMessages.sort((a, b) -> a.getTimestamp().compareTo(b.getTimestamp()));

        return ResponseEntity.ok(chatMessages);
    
        
    }

    public ResponseEntity<?> voteSurvey(ChatSurveyVoteDTO chatSurveyVoteDTO) {
        String username = chatSurveyVoteDTO.getUsername();
        int surveyId = chatSurveyVoteDTO.getSurveyId();
        int option = chatSurveyVoteDTO.getOption();



        if (!userRepository.findByUsername(username).isPresent()) {
            return ResponseEntity.badRequest().body("User does not exist");
        }

        ApplicationUser user = userRepository.findByUsername(username).get();
        int familyId = user.getFamilyId();

        if (!familyRepository.findById(familyId).isPresent()) {
            return ResponseEntity.badRequest().body("Family does not exist");
        }

        Family family = familyRepository.findById(familyId).get();

        if (!chatSurveyRepository.findById(surveyId).isPresent()) {
            return ResponseEntity.badRequest().body("Survey does not exist");
        }

        ChatSurvey survey = chatSurveyRepository.findById(surveyId).get();

        if (survey.getFamily().getId() != family.getId()) {
            return ResponseEntity.badRequest().body("Survey does not belong to the family");
        }

        if (option < 0 || option >= survey.getSurvey().size()) {
            return ResponseEntity.badRequest().body("Invalid option");
        }

        
        survey.updateSurveyResult(username,option);
        chatSurveyRepository.save(survey);


        return ResponseEntity.ok(chatSurveyVoteDTO);

    }
}
