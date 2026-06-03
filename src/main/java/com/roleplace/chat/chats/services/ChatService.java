package com.roleplace.chat.chats.services;

import com.roleplace.chat.aggregators.membership.Membership;
import com.roleplace.chat.aggregators.membership.services.MembershipService;
import com.roleplace.chat.chats.models.Chat;
import com.roleplace.chat.chats.models.requests.CreateChatRequest;
import com.roleplace.chat.messages.message.models.Message;
import com.roleplace.chat.messages.message.models.MessageRepository;
import com.roleplace.chat.users.services.UserDataService;
import exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import tools.CollectionTools;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatDataService chatDataService;
    private final CacheManager cacheManager;
    private final MessageRepository messageRepository;
    private final MembershipService membershipService;
    private final UserDataService userDataService;

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
    public List<Message> getMessagesByChatId(Long chatId) throws NotFoundException {
        if (!chatDataService.isExist(chatId))
            throw new NotFoundException("Чат", chatId.toString());
        return chatDataService.getAllMessagesById(chatId);
    }

    public Chat createChat(CreateChatRequest request) {
        Chat chat;

        chat = new Chat(request.name());
        Set<Membership> memberships = membershipService.createMemberships(chat ,request.members().usersId());
        chat.setMemberships(memberships);

        chatDataService.save(chat);

        return chat;
    }

    public long generateMessageId(Chat chat) {
        long id = chat.getMaxMessageId() + 1;
        chat.setMaxMessageId(id);
        chatDataService.updateMessageId(chat.getId(), id);
        return id;
    }

    public void addUsers(Long chatId, List<UUID> users) throws NotFoundException {
        if (!chatDataService.isExist(chatId))
            throw new NotFoundException("Чат", chatId.toString());
        List<UUID> existedUsers = userDataService.getUsersById(users);
        List<UUID> missed = users.stream()
                .filter(id -> !existedUsers.contains(id))
                .toList();
        if (!CollectionTools.isEmpty(missed))
            throw new NotFoundException("Пользователь", missed.toString());
        chatDataService.addUsers(chatId, users);
    }

    public void removeUsers(Long chatId, List<UUID> users) throws NotFoundException {
        if (!chatDataService.isExist(chatId))
            throw new NotFoundException("Чат", chatId.toString());
        List<UUID> existedUsers = userDataService.getUsersById(users);
        List<UUID> missed = users.stream()
                .filter(id -> !existedUsers.contains(id))
                .toList();
        if (!CollectionTools.isEmpty(missed))
            throw new NotFoundException("Пользователь", missed.toString());
        chatDataService.removeUsers(chatId, users);
    }

    public Chat findById(Long id)
    {
        return chatDataService.findFirstById(id);
    }
    public Set<Chat> getChatsByUser(UUID id) throws NotFoundException {
        if (!userDataService.isExist(id))
            throw new NotFoundException("Пользователь", id.toString());
        return chatDataService.getAllChatsByUser(id);
    }

}