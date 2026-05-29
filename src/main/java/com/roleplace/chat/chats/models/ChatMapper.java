package com.roleplace.chat.chats.models;

import com.roleplace.chat.messages.message.models.Message;
import com.roleplace.chat.users.models.User;
import com.roleplace.chat.messages.message.models.MessageDTO;
import com.roleplace.chat.users.models.UserDTO;

import java.util.stream.Collectors;

public class ChatMapper {

    public static UserDTO toDTO(User user) {
        return new UserDTO(user.getId(), user.getUsername());
    }

    public static MessageDTO toDTO(Message msg) {
        MessageDTO dto = new MessageDTO();
        dto.setChatId(msg.getChatId());
        dto.setContent(msg.getContent());
        dto.setUsername(msg.getUsername().getUsername());
        return dto;
    }

    public static ChatDTO toDTO(Chat chat) {
        ChatDTO dto = new ChatDTO();
        dto.setId(chat.getId());
        dto.setName(chat.getName());
        dto.setMessages(chat.getMessages()
                .stream()
                .map(ChatMapper::toDTO)
                .collect(Collectors.toList()));
        return dto;
    }
}