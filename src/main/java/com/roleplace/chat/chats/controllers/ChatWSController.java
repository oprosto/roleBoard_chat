package com.roleplace.chat.chats.controllers;

import com.roleplace.chat.chats.services.ChatService;
import com.roleplace.chat.messages.message.models.Message;
import com.roleplace.chat.messages.message.models.request.MessageRequest;
import com.roleplace.chat.messages.message.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

@Controller
@RequiredArgsConstructor
public class ChatWSController {

    private final ChatService chatService;
    private final SimpMessagingTemplate template;
    private final MessageService messageService;

    @Transactional
    @MessageMapping("/receive")
    public void receiveMessage(MessageRequest messageDTO) {
        Message message = messageParser(messageDTO);
        chatService.handleMessage(message);
        template.convertAndSend("/topic/messages." + message.getId().getChatId(), messageDTO);
    }
    private Message messageParser(MessageRequest messageDTO) {
        return messageService.createMessage(messageDTO);
    }
}