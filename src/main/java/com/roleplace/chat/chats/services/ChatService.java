package com.roleplace.chat.chats.services;

import com.roleplace.chat.chats.models.Chat;
import com.roleplace.chat.chats.models.ChatRepository;
import com.roleplace.chat.chats.models.requests.CreateChatRequest;
import com.roleplace.chat.messages.message.models.Message;
import com.roleplace.chat.messages.message.models.MessageRepository;
import exceptions.DBException;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import tools.CollectionTools;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService {

    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final CacheManager cacheManager;

    public ChatService(MessageRepository messageRepository, ChatRepository chatRepository, CacheManager cacheManager) {
        this.messageRepository = messageRepository;
        this.chatRepository = chatRepository;
        this.cacheManager = cacheManager;
    }

    public Message handleMessage(Message msg) {
        // 1. Сохраняем в БД
        messageRepository.save(msg);

        // 2. Добавляем в кэш
        Cache cache = cacheManager.getCache("chatCache");
        if (cache != null) {
            List<Message> cachedMessages = cache.get(msg.getChatId(), List.class);
            if (cachedMessages == null) cachedMessages = new ArrayList<>();
            cachedMessages.add(msg);
            cache.put(msg.getChatId(), cachedMessages);
        }

        return msg;
    }
    // Получение истории сообщений по chatId
    public List<Message> getMessagesByChatId(long chatId) {
        Cache cache = cacheManager.getCache("chatCache");
        List<Message> cachedMessages = null;
        if (cache != null) {
            cachedMessages = cache.get(chatId, List.class);
        }

        if (cachedMessages != null) {
            return cachedMessages;
        } else {
            // Если в кэше нет — подгружаем из БД
            List<Message> messagesFromDb = messageRepository.findAllByChatIdOrderByTimestampAsc(chatId);
            if (cache != null) {
                cache.put(chatId, messagesFromDb); // кладем в кэш
            }
            return messagesFromDb;
        }
    }

    public Chat createChat(CreateChatRequest request) throws DBException {
        Chat chat;
        if (CollectionTools.isEmpty(request.getMembers()))
            chat = new Chat(request.getName(), request.getCreator());
        else
            chat = new Chat(request.getName(), request.getCreator(), request.getMembers());
        try
        {
            chatRepository.save(chat);
        }catch (Exception e)
        {
            throw new DBException(e.getMessage());
        }
        return chat;
    }
}