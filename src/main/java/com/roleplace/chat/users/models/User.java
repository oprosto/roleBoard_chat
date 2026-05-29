package com.roleplace.chat.users.models;

import com.roleplace.chat.chats.models.Chat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    public User(UUID userId)
    {
        id = userId;
        isLocked = false;
    }
    public User(UUID userId, String username)
    {
        this(userId);
        this.username = username;
    }

    @Id
    private UUID id;

    @Column(unique = true, nullable = false)
    @NotBlank
    private String username;

    @Column
    private boolean isLocked;

    @ManyToMany
    @JoinTable(
            name = "memberships",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "chat_id")
    )
    private List<Chat> chats;
    //TODO сделать отдельную сущность для бд
    //@OneToMany(mappedBy = "user")
    //private List<ChatMember> memberships;
}
