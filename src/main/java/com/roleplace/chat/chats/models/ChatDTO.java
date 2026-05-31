package com.roleplace.chat.chats.models;

import com.roleplace.chat.messages.message.models.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatDTO {
    private long id;
    private String name;
    private Message lastMessage;
}