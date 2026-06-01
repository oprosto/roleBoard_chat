package com.roleplace.chat.users.services;

import com.roleplace.chat.users.models.User;
import com.roleplace.chat.users.models.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tools.CollectionTools;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserDataService {

    private final UserRepository userRepository;

    public void save(User user)
    {
        userRepository.save(user);
    }

    public User findById(UUID id)
    {
        return userRepository.findFirstById(id);
    }

    public User getReferenceById(UUID id){return userRepository.getReferenceById(id);}

    public Set<UUID> getUsersById(List<UUID> ids)
    {
        if (CollectionTools.isEmpty(ids))
            return HashSet.newHashSet(0);
        return userRepository.findAllById(ids);
    }
}
