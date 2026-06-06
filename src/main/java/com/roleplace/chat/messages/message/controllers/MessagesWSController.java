package com.roleplace.chat.messages.message.controllers;

import com.roleplace.chat.messages.attachments.services.AttachmentService;
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
public class MessagesWSController {
    private final MessageService messageService;
    private final AttachmentService attachmentService;
    private final SimpMessagingTemplate template;

    @Transactional
    @MessageMapping("/receive")
    public void receiveMessage(MessageRequest messageDTO) {
        Message message = messageParser(messageDTO);
        attachmentService.handleAttachments(message);
        template.convertAndSend("/topic/messages." + message.getId().getChatId(), messageDTO);
    }

    private Message messageParser(MessageRequest messageDTO) {
        return messageService.createMessage(messageDTO);
    }
}
