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

        fabAdd.setOnClickListener(v ->
                startActivity(new Intent(this, AddEditArticleActivity.class))
        );
//        Article testArticle = new Article();
//        testArticle.setTitle("Bài viết mẫu");
//        testArticle.setContent("Đây là nội dung");
//        testArticle.setAuthor("Admin");
//        testArticle.setStatus("Published");
//        testArticle.setPublishedAt(java.time.Instant.now());
//
//        DatabaseHandler.getInstance(this).getArticleDao().insert(testArticle);
//        loadArticles(); // Hiển thị bài viết sau khi thêm bài viết mẫu

    }

    private void loadArticles() {
        new Thread(() -> {
            try {
                List<Article> result = DatabaseHandler.getInstance(this).getArticleDao().getAll();
                runOnUiThread(() -> {
                    articleList = result;
                    adapter = new ArticleAdminAdapter(articleList,
                            article -> {
                                Intent intent = new Intent(this, AddEditArticleActivity.class);
                                intent.putExtra("article_id", article.getId());
                                startActivity(intent);
                            },
                            article -> {
                                new Thread(() -> {
                                    DatabaseHandler.getInstance(this).getArticleDao().delete(article);
                                    runOnUiThread(() -> {
                                        Toast.makeText(this, "Đã xóa bài viết", Toast.LENGTH_SHORT).show();
                                        loadArticles(); // gọi lại để refresh
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
