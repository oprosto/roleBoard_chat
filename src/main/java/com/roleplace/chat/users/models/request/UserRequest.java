package com.roleplace.chat.users.models.request;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import model.requests.IRequest;

import java.util.UUID;

@RequiredArgsConstructor
public class UserRequest implements IRequest {
    @NotBlank
    UUID userId;
}
