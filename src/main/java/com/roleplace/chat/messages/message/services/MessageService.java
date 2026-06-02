package com.roleplace.chat.messages.message.services;

import com.roleplace.chat.chats.models.Chat;
import com.roleplace.chat.chats.models.ChatRepository;
import com.roleplace.chat.chats.services.ChatService;
import com.roleplace.chat.messages.message.models.Message;
import com.roleplace.chat.messages.message.models.MessageId;
import com.roleplace.chat.messages.message.models.MessageRepository;
import com.roleplace.chat.messages.message.models.request.MessageRequest;
import com.roleplace.chat.users.models.User;
import com.roleplace.chat.users.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final CacheManager cacheManager;
    private final UserService userService;
    private final ChatService chatService;

    @Transactional
    public Message createMessage(MessageRequest message)
    {
        User user = userService.findById(message.userId());
        Chat chat = chatService.findById(message.chatId());
        Message mes = new Message(createMessageId(chat), chat, user, message.content(), LocalDateTime.now());
        messageRepository.save(mes);
        messageRepository.flush();
        chat.setLastMessage(mes);
        return mes;
    }
    private MessageId createMessageId(Chat chat){
        return new MessageId(chatService.generateMessageId(chat), chat.getId());
    }

}
