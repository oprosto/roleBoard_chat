package com.roleplace.chat.messages.message.models;

import com.roleplace.chat.users.models.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Message {

    public Message() {
        this(null);
    }
    public Message(String message) {
        this(null, message);
    }
    public Message(User sender, String message) {
        this(0L, sender, message);
    }

    public Message(long chatId, User sender, String message) {
        this.chatId = chatId;
        this.username = sender;
        content = message;
        timestamp = LocalDateTime.now();
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JdbcTypeCode(SqlTypes.BIGINT)
    private long id;

    @Id
    private long chatId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private User username;

    private String content;
    private long replyId;

    private LocalDateTime timestamp;

}
