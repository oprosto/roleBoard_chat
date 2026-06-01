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
import com.roleplace.chat.users.models.request.ManyUsersRequest;
import exceptions.DBException;
import exceptions.NotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponseException;
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

    @GetMapping("/{userId}")
    public ResponseEntity<AllChatsResponse> getChats(@PathVariable UUID userId)
    {
        List<Chat> chats = chatService.getChatsByUser(userId);
        return new ResponseEntity<>(new AllChatsResponse(chats.stream().map(this::toDTO).toList())
                , HttpStatus.OK) ;
    }

    @PostMapping("/add/users/{chatId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addUsersToChat(@PathVariable long chatId, @Valid @RequestBody ManyUsersRequest users)
    {
        try{
            chatService.addUsers(chatId, users.usersId());
        }catch (NotFoundException e)
        {
            throw new ErrorResponseException(HttpStatus.BAD_REQUEST, e.getCause());
        }
        catch (RuntimeException e)
        {
            throw new ErrorResponseException(HttpStatus.INTERNAL_SERVER_ERROR, e.getCause());
        }
    }
    @PostMapping("/remove/users/{chatId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeUsersFromChat(@PathVariable long chatId, @Valid @RequestBody ManyUsersRequest users)
    {
        try{
            chatService.removeUsers(chatId, users.usersId());
        }catch (NotFoundException e)
        {
            throw new ErrorResponseException(HttpStatus.BAD_REQUEST, e.getCause());
        }
        catch (RuntimeException e)
        {
            throw new ErrorResponseException(HttpStatus.INTERNAL_SERVER_ERROR, e.getCause());
        }
    }



    private ChatDTO toDTO(Chat chat)
    {
        return new ChatDTO(chat.getId(), chat.getName(), chat.getLastMessage());
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
//    @PostMapping("/upload/{chatId}")  
//    public ResponseEntity<AttachmentResponse> uploadFile(@RequestParam("file") MultipartFile file,
//                                                         @PathVariable("chatId") Long chatId)
//    {
//        return
//    }
}