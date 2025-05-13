package com.example.newspaper.database.dao;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.PagingSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.newspaper.models.Article;
import com.example.newspaper.pojo.ArticleWithCategory;
import com.example.newspaper.ui.adapters.view_items.ArticleViewItem;

import java.util.List;


@Dao
public interface ArticleDao {

    @Insert
    void insert(Article article);

    @Update
    void update(Article article);

    @Delete
    void delete(Article article);

    @Query("DELETE FROM article_table")
    void deleteAlls();

    @Query("SELECT * FROM article_table ORDER BY publishedAt DESC LIMIT :limit OFFSET :offset")
    List<ArticleWithCategory> getArticlesPaged(int limit, int offset);

    @Query("SELECT * FROM article_table WHERE id IN (:articleIds)")
    List<ArticleWithCategory> getArticlesByIds(List<Integer> articleIds);

    @Query("SELECT * FROM article_table WHERE " +
            "title LIKE :keyword1 OR content LIKE :keyword1 " +
            "OR title LIKE :keyword2 OR content LIKE :keyword2 " +
            "OR title LIKE :keyword3 OR content LIKE :keyword3 " +
            "ORDER BY publishedAt DESC")
    List<ArticleWithCategory> searchPostsByKeywords(String keyword1, String keyword2, String keyword3);
}
