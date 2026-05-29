package com.roleplace.chat.chats.models.responses;

import com.roleplace.chat.chats.models.Chat;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import model.responses.IResponse;

import java.util.List;

@RequiredArgsConstructor
public class ChatsResponse implements IResponse {
    @NonNull
    List<Chat> chats;
}
