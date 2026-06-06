package com.roleplace.chat.messages.attachments.controllers;

import com.roleplace.chat.messages.attachments.services.AttachmentService;
import io.awspring.cloud.s3.S3Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/attachments")
@RequiredArgsConstructor
public class AttachmentRESTController {

    private final AttachmentService attachmentService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        UUID id;
        try {
            id = attachmentService.addFile(file);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(e.getCause());
        }
        return ResponseEntity.ok().body(id);

    }
    //TODO времянка
    @PostMapping("/download/{chatId}")
    public ResponseEntity<S3Resource> downloadFile(@RequestParam("key") String key,
                                                   @PathVariable("chatId") Long chatId) {
        S3Resource file = attachmentService.downloadFile(key);
        return ResponseEntity.ok(file);
    }
}
