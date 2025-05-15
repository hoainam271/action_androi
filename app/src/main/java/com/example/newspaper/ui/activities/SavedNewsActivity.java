package com.example.newspaper.ui.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newspaper.R;
import com.example.newspaper.database.DatabaseHandler;
import com.example.newspaper.models.SavedArticle;
import com.example.newspaper.ui.adapters.SavedNewsAdapter;
import com.example.newspaper.ArticleDetailActivity;
import android.util.Log;
import java.time.Instant;
import java.util.List;

public class SavedNewsActivity extends AppCompatActivity implements SavedNewsAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private SavedNewsAdapter adapter;
    private List<SavedArticle> savedArticles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_news);

        recyclerView = findViewById(R.id.recyclerViewSaved);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadSavedArticles();
    }

    private void loadSavedArticles() {
        new Thread(() -> {
            DatabaseHandler db = DatabaseHandler.getInstance(getApplicationContext());

            // Thêm bài viết mẫu nếu bảng đang rỗng
            if (db.getSavedArticleDao().getAllSavedArticles().isEmpty()) {
                SavedArticle sample = new SavedArticle(
                        String.valueOf(9999L), // articleId (giả lập)
                        "Bài viết mẫu đã lưu",
                        "Mô tả ngắn về bài viết mẫu này...",
                        "Nội dung đầy đủ của bài viết mẫu để test chức năng đã lưu",
                        "https://cdnphoto.dantri.com.vn/gxTLQVyv-It0YTA4GYBa2_Dyf-A=/2025/04/27/gold-1745760782075.jpg",
                        "Kinh doanh",
                        System.currentTimeMillis()
                );
                db.getSavedArticleDao().insert(sample);
                Log.d("DEBUG", "Đã thêm bài viết mẫu vào bảng SavedArticle.");
            }

            savedArticles = db.getSavedArticleDao().getAllSavedArticles();

            // ✅ Log ra số lượng bài viết đã lưu
            Log.d("DEBUG", "Số bài viết đã lưu: " + savedArticles.size());

            runOnUiThread(() -> {
                adapter = new SavedNewsAdapter(savedArticles, this);
                recyclerView.setAdapter(adapter);
            });
        }).start();
    }


    @Override
    public void onItemClick(SavedArticle article) {
        Intent intent = new Intent(this, ArticleDetailActivity.class);
        intent.putExtra("articleId", article.getArticleId());
        intent.putExtra("title", article.getTitle());
        intent.putExtra("description", article.getDescription());
        intent.putExtra("content", article.getContent());
        intent.putExtra("imageUrl", article.getImageUrl());
        intent.putExtra("category", article.getCategory());
        startActivity(intent);
    }
}
