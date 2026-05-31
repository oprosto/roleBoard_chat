package com.roleplace.chat.messages.message.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MessageId implements Serializable {
    @Column(name = "message_id")
    private long messageId;
    @Column(name = "chat_id")
    private long chatId;

    @Serial
    private static final long serialVersionUID = 1L;
}
