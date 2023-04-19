package com.valerycrane.doodlebackend.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class ImageController {

    private final String userDirectory = "static/pictures/";

    @PostMapping("/images")
    public void uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(userDirectory + file.getOriginalFilename());
            Files.createDirectories(path.getParent());
            Files.write(path, bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(
        value = "/images/{name}",
        produces = MediaType.IMAGE_JPEG_VALUE
    )
    public byte[] downloadImage(@PathVariable String name) {
        try {
            Path path = Paths.get(userDirectory + name);
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
