package com.roleplace.chat.chats.models.requests;

import com.roleplace.chat.users.models.request.ManyUsersRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;

public record CreateChatRequest(
        @NotBlank String name,
        @NonNull ManyUsersRequest members
) {}
