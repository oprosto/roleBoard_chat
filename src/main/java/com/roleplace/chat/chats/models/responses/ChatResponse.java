package com.roleplace.chat.chats.models.responses;

import com.roleplace.chat.chats.models.ChatDTO;
import lombok.*;
import model.responses.IResponse;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class ChatResponse implements IResponse {
    @NonNull
    ChatDTO chat;
}
