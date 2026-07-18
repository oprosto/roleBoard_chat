package com.roleplace.chat.messages.attachments.services;

import com.roleplace.chat.messages.attachments.models.Attachment;
import com.roleplace.chat.messages.attachments.models.AttachmentResponse;
import com.roleplace.chat.messages.message.models.Message;
import com.roleplace.chat.messages.message.services.MessageService;
import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttachmentService {

    private final S3Template s3Template;
    private final AttachmentDataService attachmentDataService;
    private final MessageService messageService;
    private final CacheManager cacheManager;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucketName;

    // Загрузка файла
    public UUID uploadFile(MultipartFile file) throws IOException {
        UUID id = UUID.randomUUID();
        try (InputStream inputStream = file.getInputStream()) {
            // upload метод загружает файл в S3
            s3Template.upload(bucketName, id.toString(), inputStream);
        }
        return id;
    }

    // Скачивание файла
    public S3Resource downloadFile(String key) {
        // download возвращает S3Resource, из которого можно получить InputStream
        return s3Template.download(bucketName, key);
    }

    // Удаление файла
    public void deleteFile(String key) {
        s3Template.deleteObject(bucketName, key);
    }

    public void deleteFiles(List<Attachment> attachments) {
        for (Attachment attachment : attachments) {
            deleteFile(attachment.getId().toString());
        }
    }

    // Генерация временной ссылки (например, на 5 минут)
    public URL generatePresignedUrl(String key) {
        return s3Template.createSignedGetURL(bucketName, key, Duration.ofMinutes(5));
    }

    public AttachmentResponse addFile(MultipartFile file) throws IOException {
        UUID id = uploadFile(file);
        Attachment attachment = new Attachment(id, file.getOriginalFilename(),
                FilenameUtils.getExtension(file.getOriginalFilename()),
                file.getContentType(), file.getSize(), LocalDateTime.now());
        attachmentDataService.save(attachment);
        return new AttachmentResponse(attachment);
    }

    @Transactional
    public void handleAttachments(Message message) {
        List<Attachment> attachments = messageService.getAttachments(message.getId());
        for (Attachment attachment : attachments) {
            attachment.setTemporary(false);
            attachment.setMessage(message);
        }
        attachmentDataService.saveAll(attachments);

    }

    @Scheduled(cron = "0 1 * * * *") // каждый час
    @Transactional
    public void cleanOrphanedAttachments() {
        LocalDateTime threshold = LocalDateTime.now().minusHours(24);
        List<Attachment> orphans = attachmentDataService.getExpiredAttachments(threshold);

        deleteFiles(orphans);
        attachmentDataService.removeByIds(orphans);
    }

    //TODO Настроить кэш + возможно убрать сохранение
//    public void handleAttachments1(List<Attachment> attachments) {
//        if (CollectionTools.isEmpty(attachments))
//            return;
//        for (Attachment attachment : attachments) {
//            // 1. Сохраняем в БД
//            attachmentDataService.save(attachment);
//
//            // 2. Добавляем в кэш
//            Cache cache = cacheManager.getCache("attachmentCache");
//            if (cache != null) {
//                List<Attachment> cachedAttacments = cache.get(attachment.getId(), List.class);
//                if (cachedAttacments == null) cachedAttacments = new ArrayList<>();
//                cachedAttacments.add(attachment);
//                cache.put(attachment.getId(), cachedAttacments);
//            }
//        }
//    }
}