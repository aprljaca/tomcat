package com.tomcat.controller;

import com.tomcat.entity.UserEntity;
import com.tomcat.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1")
public class ProfileImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping(path = "/uploadImage", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file, @AuthenticationPrincipal UserEntity userEntity) throws IOException {
        String uploadImage = imageService.uploadImage(file, userEntity);
        return ResponseEntity.status(HttpStatus.OK).body(uploadImage);
    }

    @GetMapping("/downloadImage")
    public String downloadImage(@RequestParam("userId") Long userId) {
        return imageService.downloadImage(userId);
    }
}