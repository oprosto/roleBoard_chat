package com.roleplace.chat.aggregators.membership;

import com.roleplace.chat.chats.models.Chat;
import com.roleplace.chat.users.models.User;
import jakarta.persistence.*;
import lombok.*;

//Объединяет юзера и чат, разрывая связь manyToMany
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "chat_id"})
})
@AllArgsConstructor
public class Membership {
    @EmbeddedId
    private MembershipId id;
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @MapsId("chatId")
    @JoinColumn(name = "chat_id")
    Chat chat;
}
