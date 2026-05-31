package com.roleplace.chat.chats.controllers;


import com.roleplace.chat.chats.models.Chat;
import com.roleplace.chat.chats.models.ChatDTO;
import com.roleplace.chat.chats.models.requests.CreateChatRequest;
import com.roleplace.chat.chats.models.responses.AllChatsResponse;
import com.roleplace.chat.chats.models.responses.ChatResponse;
import com.roleplace.chat.chats.services.ChatService;
import com.roleplace.chat.messages.message.models.DTO.MessageDTO;
import com.roleplace.chat.messages.message.models.Message;
import com.roleplace.chat.messages.message.responses.AllMessagesResponse;
import com.roleplace.chat.users.models.UserDTO;
import exceptions.DBException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
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
                new AllMessagesResponse( messages.stream()
                        .map(this::getMessageDto)
                        .collect(Collectors.<MessageDTO>toList())), HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<ChatResponse> createChat(@Valid @RequestBody CreateChatRequest request) throws DBException {
        Chat chat = chatService.createChat(request);
        return new ResponseEntity<>(new ChatResponse(toDTO(chat)), HttpStatus.CREATED);
    }

    private MessageDTO getMessageDto(Message message)
    {
        MessageDTO dto =new MessageDTO();
        dto.setChatId(message.getId().getChatId());
        dto.setUser(new UserDTO(message.getSender().getId(), message.getSender().getNickname()));
        dto.setContent(message.getContent());
        dto.setCreatedAt(message.getCreatedAt());
        return dto;
    }
    @GetMapping("/{userId}")
    public ResponseEntity<AllChatsResponse> getChats(@PathVariable UUID userId)
    {
        List<Chat> chats = chatService.getChatsByUser(userId);
        return new ResponseEntity<>(new AllChatsResponse(chats.stream().map(this::toDTO).toList())
                , HttpStatus.OK) ;
    }

    private ChatDTO toDTO(Chat chat)
    {
        return new ChatDTO(chat.getId(), chat.getName(), chat.getLastMessage());
    }
//    @PostMapping("/upload/{chatId}")  
//    public ResponseEntity<AttachmentResponse> uploadFile(@RequestParam("file") MultipartFile file,
//                                                         @PathVariable("chatId") Long chatId)
//    {
//        return
//    }
}