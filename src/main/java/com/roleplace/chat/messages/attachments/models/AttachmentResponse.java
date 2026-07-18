package com.roleplace.chat.messages.attachments.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class AttachmentResponse {
    //MultipartFile messages;
    UUID id;            //url в том числе
    String name;
    String extension;
    String type;
    long size;

    public AttachmentResponse(Attachment attachment)
    {
        this(attachment.getId(), attachment.getName(), attachment.getExtension(), attachment.getType(), attachment.getSize());
    }
}
