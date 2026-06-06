package com.roleplace.chat.messages.attachments.models;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AttachmentRepository extends JpaRepository<Attachment, UUID> {
    List<Attachment> findByTemporaryTrueAndCreatedAtBefore(LocalDateTime threshold);

    void removeById(UUID id);

    @Transactional
    @Modifying
    @Query("DELETE FROM Attachment a WHERE a.id IN :ids")
    void removeByIds(@Param("ids") List<UUID> ids);
}
