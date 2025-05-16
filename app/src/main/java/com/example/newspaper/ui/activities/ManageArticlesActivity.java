package com.example.newspaper.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newspaper.R;
import com.example.newspaper.database.DatabaseHandler;
import com.example.newspaper.models.Article;
import com.example.newspaper.ui.adapters.ArticleAdminAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ManageArticlesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton fabAdd;
    private ArticleAdminAdapter adapter;
    private List<Article> articleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_articles);

        recyclerView = findViewById(R.id.recyclerViewArticles);
        fabAdd = findViewById(R.id.fabAddArticle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddEditArticleActivity.class);
            startActivity(intent);
        });
    }

    private void loadArticles() {
        new Thread(() -> {
            try {
                List<Article> result = DatabaseHandler.getInstance(this).getArticleDao().getAll();
                runOnUiThread(() -> {
                    articleList = result;

                    adapter = new ArticleAdminAdapter(articleList,
                            article -> {
                                // ✅ Truyền đầy đủ thông tin bài viết để sửa
                                Intent intent = new Intent(this, AddEditArticleActivity.class);
                                intent.putExtra("article_id", article.getId());
                                intent.putExtra("title", article.getTitle());
                                intent.putExtra("author", article.getAuthor());
                                intent.putExtra("summary", article.getSummary());
                                intent.putExtra("content", article.getContent());
                                intent.putExtra("thumbnailUrl", article.getThumbnailUrl());
                                startActivity(intent);
                            },
                            article -> {
                                new Thread(() -> {
                                    DatabaseHandler.getInstance(this).getArticleDao().delete(article);
                                    runOnUiThread(() -> {
                                        Toast.makeText(this, "Đã xóa bài viết", Toast.LENGTH_SHORT).show();
                                        loadArticles(); // Refresh danh sách
                                    });
                                }).start();
                            });

                    recyclerView.setAdapter(adapter);
                });
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_LONG).show());
            }
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadArticles();
    }
}
