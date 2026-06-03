package com.roleplace.chat.users.models.request;

import lombok.NonNull;
import model.requests.IRequest;

import java.util.Set;
import java.util.UUID;

public record ManyUsersRequest(@NonNull Set<UUID> usersId) implements IRequest{}
