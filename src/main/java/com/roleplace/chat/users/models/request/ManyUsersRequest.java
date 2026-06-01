package com.roleplace.chat.users.models.request;

import lombok.NonNull;
import model.requests.IRequest;

import java.util.List;
import java.util.UUID;

public record ManyUsersRequest(@NonNull List<UUID> usersId) implements IRequest{}
