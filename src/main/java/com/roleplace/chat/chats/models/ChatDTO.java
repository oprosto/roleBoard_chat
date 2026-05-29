package com.roleplace.chat.chats.models;

import com.roleplace.chat.users.models.User;
import com.roleplace.chat.messages.message.models.MessageDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
import java.util.List;

@Setter
@Getter
public class ChatDTO {
    private UUID id;
    private String name;
    private List<MessageDTO> messages;
    private List<User> users;

}