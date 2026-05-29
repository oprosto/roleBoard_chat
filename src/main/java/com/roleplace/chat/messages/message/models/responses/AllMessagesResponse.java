package com.roleplace.chat.messages.message.models.responses;

import com.roleplace.chat.messages.message.models.MessageDTO;
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
public class AllMessagesResponse implements IResponse {
    List<MessageDTO> messages;
}
