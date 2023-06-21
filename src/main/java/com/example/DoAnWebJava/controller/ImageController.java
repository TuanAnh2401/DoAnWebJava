package com.example.DoAnWebJava.controller;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping("/admin")
public class ImageController {

    private static final String UPLOAD_DIR = "src/main/resources/static/images";

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("image") MultipartFile image) throws IOException {
        if (image.isEmpty()) {
            // Handle when no image is uploaded
            return null;
        }

        String fileName = StringUtils.cleanPath(image.getOriginalFilename());
        String uploadPath = UPLOAD_DIR + File.separator + fileName;

        // Store the image in the upload directory
        Path destinationPath = Path.of(uploadPath).toAbsolutePath().normalize();
        Files.createDirectories(destinationPath.getParent());
        Files.copy(image.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

        // Return the stored image path
        return fileName;
    }
}

