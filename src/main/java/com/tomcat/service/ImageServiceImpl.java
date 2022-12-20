package com.tomcat.service;

import com.tomcat.entity.ProfileImageEntity;
import com.tomcat.entity.UserEntity;
import com.tomcat.model.Object;
import com.tomcat.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public String uploadImage(MultipartFile file, UserEntity userEntity) throws IOException {
        String folderPath = "C:/Users/Admir/Desktop/Visual Studio Code Projects/tomcat-react/public/images/";
        String imagePath = folderPath + file.getOriginalFilename();
        Optional<ProfileImageEntity> profilImageEntity = imageRepository.findByUserId(userEntity.getId());
        if (profilImageEntity.isPresent()) {
            imageRepository.delete(profilImageEntity.get());
        }
        imageRepository.save(new ProfileImageEntity(file.getOriginalFilename(), file.getContentType(), imagePath, userEntity.getId()));
        file.transferTo(new File(imagePath));
        return "file uploaded successfully : " + file.getOriginalFilename();
    }

    public String downloadImage(Long userId) {
        Optional<ProfileImageEntity> dbImageData = imageRepository.findByUserId(userId);
        String imageName = dbImageData.get().getName();
        return "http://127.0.0.1:8888/"+imageName;
    }
}
