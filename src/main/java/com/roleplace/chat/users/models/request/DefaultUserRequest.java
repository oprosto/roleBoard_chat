package com.roleplace.chat.users.models.request;

import jakarta.validation.constraints.NotBlank;
import model.requests.IRequest;

import java.util.UUID;

public record DefaultUserRequest(@NotBlank UUID userId) implements IRequest{}
