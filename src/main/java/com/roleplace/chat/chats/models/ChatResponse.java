package com.roleplace.chat.chats.models;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import model.responses.IResponse;

@RequiredArgsConstructor
@NoArgsConstructor
public class ChatResponse implements IResponse {
    @NonNull
    Chat chat;
}
