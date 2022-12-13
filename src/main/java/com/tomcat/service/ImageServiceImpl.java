package com.tomcat.service;

import com.tomcat.entity.ProfilImageEntity;
import com.tomcat.entity.UserEntity;
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
        Optional<ProfilImageEntity> profilImageEntity = imageRepository.findByUserId(userEntity.getId());
        if (profilImageEntity.isPresent()) {
            imageRepository.delete(profilImageEntity.get());
        }
        imageRepository.save(new ProfilImageEntity(file.getOriginalFilename(), file.getContentType(), imagePath, userEntity.getId()));
        file.transferTo(new File(imagePath));
        return "file uploaded successfully : " + file.getOriginalFilename();
    }

    public String downloadImage(Long userId) {
        Optional<ProfilImageEntity> dbImageData = imageRepository.findByUserId(userId);
        return dbImageData.get().getName();
    }
}
