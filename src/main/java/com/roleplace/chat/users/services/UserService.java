package com.roleplace.chat.users.services;

import com.roleplace.chat.aggregators.membership.MembershipRepository;
import com.roleplace.chat.chats.models.Chat;
import com.roleplace.chat.users.models.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {
    private final UserDataService userDataService;
    private final MembershipRepository membershipRepository;

    //TODO добавить кэширование
    public List<Chat> getAllChats(UUID userId) {
        return membershipRepository.findUserChats(userId);
    }

    public User findById(UUID id) {
        return userDataService.findById(id);
    }
}
