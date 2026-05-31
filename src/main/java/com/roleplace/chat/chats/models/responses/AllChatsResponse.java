package com.roleplace.chat.chats.models.responses;

import com.roleplace.chat.chats.models.ChatDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.responses.IResponse;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AllChatsResponse implements IResponse {
    List<ChatDTO> chats;
}
