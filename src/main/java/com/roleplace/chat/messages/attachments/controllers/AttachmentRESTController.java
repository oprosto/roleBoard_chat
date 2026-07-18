package com.roleplace.chat.messages.attachments.controllers;

import com.roleplace.chat.messages.attachments.models.AttachmentResponse;
import com.roleplace.chat.messages.attachments.services.AttachmentService;
import io.awspring.cloud.s3.S3Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/chat/attachments")
@RequiredArgsConstructor
public class AttachmentRESTController {

    private final AttachmentService attachmentService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        AttachmentResponse response;
        try {
            response = attachmentService.addFile(file);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(e.getCause());
        }
        return ResponseEntity.ok().body(response);

    }
    //TODO времянка
    @PostMapping("/download/{chatId}")
    public ResponseEntity<S3Resource> downloadFile(@RequestParam("key") String key) {
        S3Resource file = attachmentService.downloadFile(key);
        return ResponseEntity.ok(file);
    }
}
