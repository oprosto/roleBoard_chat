package com.roleplace.chat.chats.models;

import com.roleplace.chat.aggregators.membership.Membership;
import com.roleplace.chat.messages.message.models.Message;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chat {
    //Подумать над NoArgsConstructor могут быть баги
    //id long т.к. предполагается, что будет использоваться только внутри chat-service
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank
    @Column
    private String name;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Membership> memberships;

    @Column
    private long maxMessageId = 0;

    @OneToOne
    private Message lastMessage;

    public Chat(String name)
    {
        this.name = name;
    }
}
