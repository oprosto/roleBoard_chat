package com.roleplace.chat.chats.controllers;


import com.roleplace.chat.chats.models.Chat;
import com.roleplace.chat.chats.models.requests.CreateChatRequest;
import com.roleplace.chat.chats.models.responses.AllChatsResponse;
import com.roleplace.chat.chats.models.responses.ChatResponse;
import com.roleplace.chat.chats.services.ChatService;
import com.roleplace.chat.messages.message.models.DTO.MessageDTO;
import com.roleplace.chat.messages.message.models.Message;
import com.roleplace.chat.messages.message.responses.AllMessagesResponse;
import com.roleplace.chat.users.models.request.ManyUsersRequest;
import exceptions.NotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRESTController {

    private final ChatService chatService;

    @GetMapping("/{chatId}/messages")
    public ResponseEntity<?> getChatMessages(@PathVariable long chatId) {
        try {
            List<Message> messages = chatService.getMessagesByChatId(chatId);
            return ResponseEntity.ok(new AllMessagesResponse(messages.stream()
                    .map(MessageDTO::new)
                    .collect(Collectors.<MessageDTO>toList())));
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/create")
    public ResponseEntity<?> createChat(@Valid @RequestBody CreateChatRequest request) {
        try {
            Chat chat = chatService.createChat(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ChatResponse(chatService.getChatDTO(chat)));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getChats(@PathVariable UUID userId)
    {
        try {
            Set<Chat> chats = chatService.getChatsByUser(userId);
            return ResponseEntity.ok()
                    .body(new AllChatsResponse(chats.stream().map(chatService::getChatDTO).toList()));
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/add/users/{chatId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addUsersToChat(@PathVariable long chatId, @Valid @RequestBody ManyUsersRequest users)
    {
        try{
            chatService.addUsers(chatId, users.usersId().stream().toList());
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
            chatService.removeUsers(chatId, users.usersId().stream().toList());
        }catch (NotFoundException e)
        {
            throw new ErrorResponseException(HttpStatus.BAD_REQUEST, e.getCause());
        }
        catch (RuntimeException e)
        {
            throw new ErrorResponseException(HttpStatus.INTERNAL_SERVER_ERROR, e.getCause());
        }
    }
}