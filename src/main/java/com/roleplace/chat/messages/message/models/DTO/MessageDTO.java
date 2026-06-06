package com.roleplace.chat.messages.message.models.DTO;

import com.roleplace.chat.messages.message.models.Message;
import com.roleplace.chat.users.models.UserDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class MessageDTO {
    private long messageId;
    private long chatId;
    private UserDTO user;
    private String content;
    private LocalDateTime createdAt;

    public MessageDTO(Message message) {
        if (message == null)
            return;
        this.setMessageId(message.getId().getMessageId());
        this.setChatId(message.getId().getChatId());
        this.setUser(new UserDTO(message.getSender().getId(), message.getSender().getNickname()));
        this.setContent(message.getContent());
        this.setCreatedAt(message.getCreatedAt());
    }
}