package com.roleplace.chat.chats.models;

import com.roleplace.chat.messages.message.models.Message;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Chat c SET c.maxMessageId = :messageId WHERE c.id = :chatId")
    void updateLastMessageId(@Param("chatId") Long chatId, @Param("messageId") Long messageId);

    Chat findFirstById(long id);

    @Query("SELECT m FROM Message m WHERE m.id.chatId = :chatId ORDER BY m.createdAt DESC")
    List<Message> findAllByIdOrderByTimestampDesc(@Param("chatId") long id);

    @Query("SELECT c.chat FROM Membership c WHERE c.id.userId = :userId")
    Set<Chat> findAllByUserId(@Param("userId") UUID id);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO membership(chat_id, user_id) VALUES (:chatId, unnest(:userIds)) ON CONFLICT (chat_id, user_id) do nothing", nativeQuery = true)
    void addUsers(@Param("chatId") Long chatId, @Param("userIds") UUID[] ids);

    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "delete from membership where chat_id = :chatId and user_id IN :userIds", nativeQuery = true)
    void removeUsers(@Param("chatId") Long chatId, @Param("userIds") List<UUID> userIds);

    Boolean existsById(long id);
}
