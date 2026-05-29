package com.roleplace.chat.messages.message.models;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByChatIdOrderByTimestampAsc(long chatId);
}
