package com.roleplace.chat.chats.models;

import com.roleplace.chat.messages.message.models.Message;

public record ChatDTO (
    long id,
    String name,
    Message lastMessage
){}