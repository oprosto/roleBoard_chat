package com.roleplace.chat.messages.message.services;

import com.roleplace.chat.chats.models.Chat;
import com.roleplace.chat.chats.services.ChatDataService;
import com.roleplace.chat.chats.services.ChatService;
import com.roleplace.chat.messages.attachments.models.Attachment;
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
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final CacheManager cacheManager;
    private final UserService userService;
    private final ChatDataService chatDataService;
    private final ChatService chatService;

    //TODO Настроить кэш
//    public Message handleMessage(Message msg) {
//        // 1. Сохраняем в БД
//        messageRepository.save(msg);
//
//        // 2. Добавляем в кэш
//        Cache cache = cacheManager.getCache("chatCache");
//        if (cache != null) {
//            List<Message> cachedMessages = cache.get(msg.getId().getChatId(), List.class);
//            if (cachedMessages == null) cachedMessages = new ArrayList<>();
//            cachedMessages.add(msg);
//            cache.put(msg.getId().getChatId(), cachedMessages);
//        }
//
//        return msg;
//    }

    @Transactional
    public Message createMessage(MessageRequest message) {
        User user = userService.findById(message.userId());
        Chat chat = chatService.findById(message.chatId());
        Message mes = new Message(createMessageId(chat), chat, user, message.content(), LocalDateTime.now());
        messageRepository.saveAndFlush(mes);
        chat.setLastMessage(mes);
        chatDataService.save(chat);
        return mes;
    }

    public long generateMessageId(Chat chat) {
        long id = chat.getMaxMessageId() + 1;
        chat.setMaxMessageId(id);
        chatDataService.updateMessageId(chat.getId(), id);
        return id;
    }

    public List<Attachment> getAttachments(MessageId messageId) {
        return messageRepository.getAllAttachments(messageId);
    }

    private MessageId createMessageId(Chat chat) {
        return new MessageId(generateMessageId(chat), chat.getId());
    }
//    public MessageDTO getMessageDTO(Message message)
//    {
//        if (message == null)
//            return null;
//
//        MessageDTO dto =new MessageDTO();
//        dto.setMessageId(message.getId().getMessageId());
//        dto.setChatId(message.getId().getChatId());
//        dto.setUser(new UserDTO(message.getSender().getId(), message.getSender().getNickname()));
//        dto.setContent(message.getContent());
//        dto.setCreatedAt(message.getCreatedAt());
//        return dto;
//    }
}
