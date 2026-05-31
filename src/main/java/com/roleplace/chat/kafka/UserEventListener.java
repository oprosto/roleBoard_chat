package com.roleplace.chat.kafka;

import com.roleplace.chat.users.models.User;
import com.roleplace.chat.users.services.UserDataService;
import kafka.events.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserEventListener {

    private final UserDataService userDataService;
    private final Logger logger = LoggerFactory.getLogger(UserEventListener.class);

    @KafkaListener(topics = "user-registered", groupId = "user-group")
    public void handle(UserRegisteredEvent event) {
        logger.info("User {} joined the chat", event.getLogin());
        createUser(event.getUserId(), event.getLogin(), event.getLogin());
    }

    private void createUser(UUID userId, String login, String tag)
    {
        User user = new User(userId,login, tag, false);
        userDataService.save(user);
    }

}