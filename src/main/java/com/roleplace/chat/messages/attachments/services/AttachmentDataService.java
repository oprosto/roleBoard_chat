package com.roleplace.chat.messages.attachments.services;

import com.roleplace.chat.messages.attachments.models.Attachment;
import com.roleplace.chat.messages.attachments.models.AttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttachmentDataService {
    private final AttachmentRepository attachmentRepository;

    public void save(Attachment attachment) {
        attachmentRepository.save(attachment);
    }

    public void saveAll(List<Attachment> attachments) {
        attachmentRepository.saveAll(attachments);
    }

    public List<Attachment> getExpiredAttachments(LocalDateTime threshold) {
        return attachmentRepository.findByTemporaryTrueAndCreatedAtBefore(threshold);
    }

    public void removeById(UUID attachmentId) {
        attachmentRepository.removeById(attachmentId);
    }

    public void removeByIds(List<Attachment> attachmentsIds) {
        attachmentRepository.removeByIds(attachmentsIds.stream().map(Attachment::getId).toList());
    }
}
