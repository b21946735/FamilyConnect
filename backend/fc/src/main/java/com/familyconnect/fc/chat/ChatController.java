package com.familyconnect.fc.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;


    @PostMapping("/sendMessage")
    public ResponseEntity<?> sendMessage(@RequestBody ChatMessageDTO chatMessageDTO) {
        ResponseEntity<?> response = chatService.sendMessage(chatMessageDTO);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    @PostMapping("/sendSurvey")
    public ResponseEntity<?> sendSurvey(@RequestBody ChatSurveyDTO chatSurveyDTO) {
        ResponseEntity<?> response = chatService.sendSurvey(chatSurveyDTO);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    @GetMapping("/getMessages")
    public ResponseEntity<?> getMessages(@RequestParam String username) {
        ResponseEntity<?> response = chatService.getMessages(username);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    
    @PostMapping("/voteSurvey")
    public ResponseEntity<?> voteSurvey(@RequestBody ChatSurveyVoteDTO chatSurveyVoteDTO) {
        ResponseEntity<?> response = chatService.voteSurvey(chatSurveyVoteDTO);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }





    

}
