package com.tomcat.service;

import com.tomcat.entity.UserEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    String uploadImage(MultipartFile file, UserEntity userEntity) throws IOException;

    String downloadImage(Long id);
}
