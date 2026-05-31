package com.roleplace.chat.chats.services;

import com.roleplace.chat.chats.models.Chat;
import com.roleplace.chat.chats.models.ChatRepository;
import com.roleplace.chat.messages.message.models.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatDataService {
    private final ChatRepository chatRepository;

    public void save(Chat chat)
    {
        chatRepository.save(chat);
    }
    public Chat findFirstById(long id)
    {
        return chatRepository.findFirstById(id);
    }
    public void updateMessageId(long chatId, long messageId)
    {
        chatRepository.updateLastMessageId(chatId, messageId);
    }
    public List<Message> getAllMessagesById(long id){return chatRepository.findAllByIdOrderByTimestampDesc(id);}
    public List<Chat> getAllChatsByUser(UUID id){return chatRepository.findAllByUserId(id);}
}
