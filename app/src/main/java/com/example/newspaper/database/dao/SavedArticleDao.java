package com.example.newspaper.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.newspaper.models.SavedArticle;

import java.util.List;

@Dao
public interface SavedArticleDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(SavedArticle article);


    @Delete
    void delete(SavedArticle article);


    @Query("SELECT * FROM saved_articles")
    List<SavedArticle> getAllSavedArticles();

    @Query("SELECT * FROM saved_articles WHERE articleId = :articleId LIMIT 1")
    SavedArticle getSavedArticleByArticleId(String articleId);
    @Query("DELETE FROM saved_articles WHERE articleId = :articleId")
    void deleteByArticleId(String articleId);



}
