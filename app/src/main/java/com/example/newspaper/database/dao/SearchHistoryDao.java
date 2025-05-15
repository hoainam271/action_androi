package com.example.newspaper.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.newspaper.models.SearchHistory;

import java.util.List;

@Dao
public interface SearchHistoryDao {
    @Insert
    void insert(SearchHistory searchHistory);

    @Update
    void update(SearchHistory searchHistory);

    @Delete
    void delete(SearchHistory searchHistory);

    @Query("DELETE FROM search_history_table")
    void deleteAlls();

    // ✅ Sửa lỗi: thêm điều kiện rõ ràng
    @Query("SELECT * FROM search_history_table WHERE userId = :userId")
    List<SearchHistory> getSearchHistoryByUserId(int userId);

    // ✅ Sửa lỗi: chỉ lấy cột keyword để trả về List<String>
    @Query("SELECT keyword FROM search_history_table GROUP BY keyword ORDER BY COUNT(*) DESC LIMIT 10")
    List<String> getSearchTrends();
}
