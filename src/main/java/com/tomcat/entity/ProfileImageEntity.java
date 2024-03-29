package com.tomcat.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="profil_images")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type;

    private String imagePath;

    @Column(name = "user_id")
    private Long userId;

    public ProfileImageEntity(String name, String type, String imagePath, Long userId) {
        this.name = name;
        this.type = type;
        this.imagePath = imagePath;
        this.userId = userId;
    }
}
