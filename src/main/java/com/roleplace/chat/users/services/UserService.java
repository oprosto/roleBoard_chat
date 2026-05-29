package com.roleplace.chat.users.services;

import com.roleplace.chat.chats.models.Chat;
import com.roleplace.chat.users.models.User;
import com.roleplace.chat.users.models.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {
    UserRepository userRepository;

    public List<Chat> getAllChats(UUID userId)
    {
        User user = userRepository.findFirstById(userId);
        return user.getChats();
    }
}
