package com.roleplace.chat.messages.message.models.request;

import java.util.UUID;

public record MessageRequest (
        long chatId,
        UUID userId,
        UUID clientId,
        String content
)
{}
