package com.example.newspaper;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newspaper.database.DatabaseHandler;
import com.example.newspaper.models.SavedArticle;
import com.example.newspaper.pojo.ArticleWithCategory;
import com.example.newspaper.ui.adapters.ArticleRecycleViewAdapter;
import com.example.newspaper.ui.adapters.SavedNewsAdapter;
import com.example.newspaper.ui.adapters.view_items.ArticleViewItem;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity implements SavedNewsAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private SavedNewsAdapter savedNewsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        findViewById(R.id.back_icon).setOnClickListener(v -> finish());

        TextView title = findViewById(R.id.title);
        recyclerView = findViewById(R.id.listViews);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String type = getIntent().getStringExtra("type");
        if ("history".equals(type)) {
            title.setText("Bài viết đã xem");
            // TODO: Nếu có lịch sử xem, xử lý ở đây
        } else if ("saved".equals(type)) {
            title.setText("Bài viết đã lưu");
            loadSavedArticles();
        }

    }

    private void loadSavedArticles() {
        new Thread(() -> {
            List<SavedArticle> savedArticles = DatabaseHandler.getInstance(this).getSavedArticleDao().getAllSavedArticles();
            runOnUiThread(() -> {
                savedNewsAdapter = new SavedNewsAdapter(savedArticles, this);
                recyclerView.setAdapter(savedNewsAdapter);
            });
        }).start();
    }

    @Override
    public void onItemClick(SavedArticle article) {
        Intent intent = new Intent(this, NewsDetailActivity.class);
        intent.putExtra("articleId", article.getArticleId());
        intent.putExtra("title", article.getTitle());
        intent.putExtra("description", article.getDescription());
        intent.putExtra("content", article.getContent());
        intent.putExtra("imageUrl", article.getImageUrl());
        intent.putExtra("category", article.getCategory());
        startActivity(intent);
    }
}
