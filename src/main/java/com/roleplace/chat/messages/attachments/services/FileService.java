package com.roleplace.chat.messages.attachments.services;

import exceptions.NotFoundException;
import org.springframework.web.multipart.MultipartFile;
import tools.FileTools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileService {
    private final String DIRECTORY = "uploads/";

    //Загрузка пока на диск
    public void uploadFile(MultipartFile file) throws NotFoundException {
        try {
            if (file == null)
                throw new NotFoundException("Файл не получен!");

            String extension = FileTools.getExtension(file.getOriginalFilename());
            String generatedName = FileTools.generateFileName(extension);
            Path path = Paths.get(DIRECTORY, generatedName);
            Files.copy(file.getInputStream(), path);
        }catch (IOException e)
        {
            throw new RuntimeException(e.getMessage());
        }

    }
    //Загрузка в ДБ
    public void saveToDB(MultipartFile file)
    {

    }
}
