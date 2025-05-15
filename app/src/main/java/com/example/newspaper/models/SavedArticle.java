package com.example.newspaper.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "saved_articles",
        indices = {@Index(value = "articleId", unique = true)} // ✅ articleId duy nhất
)
public class SavedArticle {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "articleId")
    private String articleId; // ID gốc của bài viết

    private String title;
    private String description;
    private String content;
    private String imageUrl;
    private String category;
    private long savedAt; // lưu timestamp thời gian lưu

    // Constructors
    public SavedArticle() {
    }

    public SavedArticle(String articleId, String title, String description, String content,
                        String imageUrl, String category, long savedAt) {
        this.articleId = articleId;
        this.title = title;
        this.description = description;
        this.content = content;
        this.imageUrl = imageUrl;
        this.category = category;
        this.savedAt = savedAt;
    }

    // Getters & Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getSavedAt() {
        return savedAt;
    }

    public void setSavedAt(long savedAt) {
        this.savedAt = savedAt;
    }
}
