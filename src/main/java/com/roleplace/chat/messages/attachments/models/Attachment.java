package com.roleplace.chat.messages.attachments.models;

import com.roleplace.chat.messages.message.models.Message;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "attachments")
public class Attachment {
    @Id
    private UUID id;
    @Column
    @Size(max = 255)
    private String name;
    @Column
    @Size(max = 128)
    private String type;
    @Column
    @Max(1024 * 1024)
    private long size;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "message_chat_id"),
            @JoinColumn(name = "message_message_id")
    })
    private Message message;
    @Column
    private boolean temporary = true;
    @Column
    private LocalDateTime createdAt;

    public Attachment(UUID id, String name, String type, long size, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.size = size;
        this.createdAt = createdAt;
    }
}
