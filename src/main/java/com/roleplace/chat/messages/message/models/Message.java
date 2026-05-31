package com.roleplace.chat.messages.message.models;

import com.roleplace.chat.chats.models.Chat;
import com.roleplace.chat.users.models.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Message {

    @EmbeddedId
    private MessageId id;

    @ManyToOne
    @MapsId("chatId")
    @JoinColumn(name = "chat_id")
    @NonNull
    private Chat chat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    @NonNull
    private User sender;

    @Column
    @NonNull
    private String content;
    @Column
    private Long replyId;
    @Column
    @NonNull
    private LocalDateTime createdAt;

    public Message(MessageId id, @NonNull Chat chat, @NonNull User sender, @NonNull String content, @NonNull LocalDateTime createdAt)
    {
        this.id = id;
        this.chat = chat;
        this.sender = sender;
        this.content = content;
        this.createdAt = createdAt;
    }
}
