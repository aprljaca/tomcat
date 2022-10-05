package com.tomcat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
@Data
@Getter
@AllArgsConstructor
public class SetLikeRequest {
    private Long postId;
    private Long userId;
}
