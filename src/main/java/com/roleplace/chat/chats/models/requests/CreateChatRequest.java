package com.roleplace.chat.chats.models.requests;

import com.roleplace.chat.users.models.User;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
public class CreateChatRequest {
    @NotBlank
    private String name;
    @NotBlank
    private User creator;
    private List<User> members;

    public CreateChatRequest(String name, User creator)
    {
        this.name = name;
        this.creator = creator;
    }
    public CreateChatRequest(String name, User creator, List<User> members)
    {
        this(name, creator);
        this.members = members;
    }
}
