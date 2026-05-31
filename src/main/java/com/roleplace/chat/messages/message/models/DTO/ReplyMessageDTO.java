package com.roleplace.chat.messages.message.models.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class ReplyMessageDTO extends MessageDTO {
    private UUID replyId;
}