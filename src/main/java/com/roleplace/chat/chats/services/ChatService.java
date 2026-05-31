package com.roleplace.chat.chats.services;

import com.roleplace.chat.aggregators.membership.Membership;
import com.roleplace.chat.aggregators.membership.services.MembershipService;
import com.roleplace.chat.chats.models.Chat;
import com.roleplace.chat.chats.models.requests.CreateChatRequest;
import com.roleplace.chat.messages.message.models.Message;
import com.roleplace.chat.messages.message.models.MessageRepository;
import exceptions.DBException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatDataService chatDataService;
    private final CacheManager cacheManager;
    private final MessageRepository messageRepository;
    private final MembershipService membershipService;

    //TODO REWORK
    public Message handleMessage(Message msg) {
        // 1. Сохраняем в БД
        messageRepository.save(msg);

        // 2. Добавляем в кэш
        Cache cache = cacheManager.getCache("chatCache");
        if (cache != null) {
            List<Message> cachedMessages = cache.get(msg.getId().getChatId(), List.class);
            if (cachedMessages == null) cachedMessages = new ArrayList<>();
            cachedMessages.add(msg);
            cache.put(msg.getId().getChatId(), cachedMessages);
        }

        return msg;
    }
    /**
    //TODO REWORK?
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
     **/
    public List<Message> getMessagesByChatId(long chatId) {
        //Chat chat = chatDataService.findFirstById(chatId);
        //return getMessagesByChat(chat);
        return chatDataService.getAllMessagesById(chatId);
    }

    public Chat createChat(CreateChatRequest request) throws DBException {
        Chat chat;

        chat = new Chat(request.getName());

        List<Membership> memberships = membershipService.createMemberships(chat ,request.getMembers());

        chat.setMemberships(memberships);

        try {
            chatDataService.save(chat);
        } catch (Exception e) {
            throw new DBException(e.getMessage());
        }

        return chat;
    }

    public long generateMessageId(Chat chat) {
        long id = chat.getMaxMessageId() + 1;
        chat.setMaxMessageId(id);
        chatDataService.updateMessageId(chat.getId(), id);
        return id;
    }

    public Chat findById(long id)
    {
        return chatDataService.findFirstById(id);
    }
    public List<Chat> getChatsByUser(UUID id){return chatDataService.getAllChatsByUser(id);}
}