package com.roleplace.chat.messages.message.models.DTO;

import com.roleplace.chat.users.models.UserDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class MessageDTO {
    private long messageId;
    private long chatId;
    private UserDTO user;
    private String content;
    private LocalDateTime createdAt;
}