package com.example.newspaper.models;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    private Integer id;
    private String title;
    private String summary;
    private String content;
    private String thumbnailUrl;
    private Integer categoryId;
    private Integer viewCount;
    private LocalDate publishedAt;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
