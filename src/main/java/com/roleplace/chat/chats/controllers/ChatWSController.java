package com.roleplace.chat.chats.controllers;

import com.roleplace.chat.messages.message.models.Message;
import com.roleplace.chat.users.models.User;
import com.roleplace.chat.messages.message.models.MessageDTO;
import com.roleplace.chat.users.models.UserRepository;
import com.roleplace.chat.chats.services.ChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

@Controller
public class ChatWSController {

    private final ChatService chatService;
    private final SimpMessagingTemplate template;
    private final UserRepository userRepository;

    ChatWSController(ChatService chatService, UserRepository userRepository, SimpMessagingTemplate template) {
        this.chatService = chatService;
        this.userRepository = userRepository;
        this.template = template;
    }

    @Transactional
    @MessageMapping("/send")
    public void sendMessage(MessageDTO messageDTO) {
        Message message = messageParser(messageDTO);
        chatService.handleMessage(message);
        template.convertAndSend("/topic/messages." + message.getChatId(), messageDTO);
    }
    private Message messageParser(MessageDTO messageDTO) {
        User user = userRepository.findFirstByUsername(messageDTO.getUsername());
        return new Message(messageDTO.getChatId(), user, messageDTO.getContent());
    }
}