package com.roleplace.chat.chats.controllers;

import com.roleplace.chat.chats.models.ChatResponse;
import com.roleplace.chat.chats.models.requests.CreateChatRequest;
import com.roleplace.chat.chats.services.ChatService;
import com.roleplace.chat.messages.message.models.Message;
import com.roleplace.chat.messages.message.models.MessageDTO;
import com.roleplace.chat.messages.message.models.responses.AllMessagesResponse;
import exceptions.DBException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chat")
public class ChatRESTController {

    private final ChatService chatService;

    public ChatRESTController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/{chatId}/messages")
    public ResponseEntity<AllMessagesResponse> getChatMessages(@PathVariable long chatId) {
        List<Message> messages = chatService.getMessagesByChatId(chatId);
        return (ResponseEntity<AllMessagesResponse>) new ResponseEntity(
                new AllMessagesResponse( messages.stream().map(msg -> {
                    MessageDTO dto = new MessageDTO();
                    dto.setChatId(msg.getChatId());
                    dto.setUsername(msg.getUsername().getUsername());
                    dto.setContent(msg.getContent());
                    return dto;
                }).collect(Collectors.<MessageDTO>toList())), HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<ChatResponse> createChat(@Valid @RequestBody CreateChatRequest request) throws DBException {
        return new ResponseEntity<>(new ChatResponse(chatService.createChat(request)), HttpStatus.CREATED);
    }

//    @PostMapping("/upload/{chatId}")  
//    public ResponseEntity<AttachmentResponse> uploadFile(@RequestParam("file") MultipartFile file,
//                                                         @PathVariable("chatId") Long chatId)
//    {
//        return
//    }
}