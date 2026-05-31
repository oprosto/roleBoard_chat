package com.roleplace.chat.aggregators.membership;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MembershipId implements Serializable {
    @Column(name = "user_id")
    private UUID userId;
    @Column(name = "chat_id")
    private long chatId;

    @Serial
    private static final long serialVersionUID = 1L;
}
