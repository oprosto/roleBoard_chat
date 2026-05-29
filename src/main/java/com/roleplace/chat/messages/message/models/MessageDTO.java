package com.roleplace.chat.messages.message.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MessageDTO {
    private long chatId;
    private String content;
    private String username;
}