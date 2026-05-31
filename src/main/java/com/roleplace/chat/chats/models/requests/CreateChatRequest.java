package com.roleplace.chat.chats.models.requests;

import com.roleplace.chat.users.models.UserDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;

@Getter
public class CreateChatRequest {
    @NotBlank
    private final String name;
    @NonNull
    private final List<UserDTO> members;

    public CreateChatRequest(String name, @NonNull List<UserDTO> members)
    {
        this.name = name;
        this.members = members;
    }
}
