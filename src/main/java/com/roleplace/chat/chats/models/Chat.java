package com.roleplace.chat.chats.models;

import com.roleplace.chat.users.models.User;
import com.roleplace.chat.messages.message.models.Message;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID id;

    @NonNull
    private String name;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    @NonNull
    private User creator;

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
    private List<Message> messages;

    @ManyToMany
    @JoinTable(
            name = "subscribers",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "chat_id")
    )
    private List<User> members;

    public Chat(@NonNull String name, @NonNull User creator)
    {
        this.name = name;
        this.creator = creator;
        members = new ArrayList<>();
        members.add(creator);
    }
    public Chat(String name, User creator, List<User> members)
    {
        this(name, creator);
        this.members.addAll(members);
    }
}
