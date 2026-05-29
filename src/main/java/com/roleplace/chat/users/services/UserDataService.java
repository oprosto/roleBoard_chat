package com.roleplace.chat.users.services;

import com.roleplace.chat.users.models.User;
import com.roleplace.chat.users.models.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDataService {

    private final UserRepository userRepository;

    public void save(User user)
    {
        userRepository.save(user);
    }
}
