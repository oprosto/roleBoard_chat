package com.roleplace.chat.messages.message.models;

import com.roleplace.chat.messages.attachments.models.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("SELECT m.attachments FROM Message m where m.id = :messageId")
    List<Attachment> getAllAttachments(@Param("messageId") MessageId messageId);
}
