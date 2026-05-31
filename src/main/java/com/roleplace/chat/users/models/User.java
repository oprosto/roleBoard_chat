package com.roleplace.chat.users.models;

import com.roleplace.chat.aggregators.membership.Membership;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String nickname;

    @Column(unique = true, nullable = false)
    private String userTag;

    @Column(nullable = false)
    private Boolean locked;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Membership> memberships;

    public User(UUID id, String nickname, String userTag, Boolean isLocked)
    {
        this.id = id;
        this.nickname = nickname;
        this.userTag = userTag;
        this.locked = isLocked;
    }
}
