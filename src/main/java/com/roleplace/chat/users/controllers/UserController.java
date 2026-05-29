package com.roleplace.chat.users.controllers;

import com.roleplace.chat.chats.models.responses.ChatsResponse;
import com.roleplace.chat.users.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    UserService userService;
    @GetMapping("/getChats")
    public ResponseEntity<ChatsResponse> getChats(@Valid @RequestBody UUID userId)
    {
        return new ResponseEntity<>(new ChatsResponse(userService.getAllChats(userId)), HttpStatus.OK);
    }
}
