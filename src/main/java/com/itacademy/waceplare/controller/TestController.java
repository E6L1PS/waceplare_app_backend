package com.itacademy.waceplare.controller;

import com.itacademy.waceplare.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/upload")
@RequiredArgsConstructor
public class TestController {

    @GetMapping
    public UserInfo test() {
        return UserInfo.builder()
                .id(1l)
                .email("sad")
                .number("sad")
                .rating(2)
                .firstname("sad")
                .lastname("sad")
                .dateOfCreated(LocalDate.MIN)
                .build();
    }

    @PostMapping
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            Path path = Paths.get("D:\\Images" + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file");
        }
        return ResponseEntity.ok("File uploaded successfully");
    }


    @PostMapping(value = "/more")
    public ResponseEntity<String> uploadImages(@RequestParam("files") List<MultipartFile> files) {
        try {
            for (MultipartFile file : files) {
                Path path = Paths.get("D:\\Images", file.getOriginalFilename());

                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                // Files.write(path, file.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка сохранения изображений");
        }

        return ResponseEntity.ok("Изображения загружены");
    }
}
