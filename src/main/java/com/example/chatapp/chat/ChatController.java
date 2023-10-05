package com.example.chatapp.chat;

import com.example.chatapp.repo.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ChatController {

    @Autowired
    private     MessageRepository messageRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage  sendMessage(@Payload ChatMessage chatMessage){
        messageRepository.save(chatMessage);

        sendUpdatedMessages();
        return chatMessage;
    }

    private void sendUpdatedMessages() {
        List<String> allMessages = messageRepository.findAllMessages();
        simpMessagingTemplate.convertAndSend("/topic/data.response", allMessages);

    }


    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor){
        headerAccessor.getSessionAttributes().put("username",chatMessage.getSender());
        return chatMessage;
    }
}
