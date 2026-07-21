package com.roleplace.chat.chats.services;

import com.roleplace.chat.aggregators.membership.Membership;
import com.roleplace.chat.aggregators.membership.services.MembershipService;
import com.roleplace.chat.chats.models.Chat;
import com.roleplace.chat.chats.models.ChatDTO;
import com.roleplace.chat.chats.models.requests.CreateChatRequest;
import com.roleplace.chat.messages.message.models.DTO.MessageDTO;
import com.roleplace.chat.messages.message.models.Message;
import com.roleplace.chat.users.models.User;
import com.roleplace.chat.users.services.UserDataService;
import exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tools.CollectionTools;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatDataService chatDataService;
    private final MembershipService membershipService;
    private final UserDataService userDataService;

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

    public void addUsers(Long chatId, List<UUID> users) throws NotFoundException {
        if (!chatDataService.isExist(chatId))
            throw new NotFoundException("Чат", chatId.toString());
        Set<UUID> existedUsers = userDataService.getExistedUserIds(users);
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
        Set<UUID> existedUsers = userDataService.getExistedUserIds(users);
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
    public ChatDTO getChatDTO(Chat chat)
    {
        return new ChatDTO(chat.getId(), chat.getName(), new MessageDTO(chat.getLastMessage()));
    }
}