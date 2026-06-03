package com.roleplace.chat.chats.models;

import com.roleplace.chat.messages.message.models.DTO.MessageDTO;

public record ChatDTO (
    long id,
    String name,
    MessageDTO lastMessage
){}