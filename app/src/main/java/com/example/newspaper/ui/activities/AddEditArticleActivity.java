package com.example.newspaper.ui.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newspaper.R;
import com.example.newspaper.database.DatabaseHandler;
import com.example.newspaper.models.Article;

import java.time.Instant;

public class AddEditArticleActivity extends AppCompatActivity {

    EditText edtTitle, edtContent, edtAuthor, edtThumbnail;
    Button btnSave;
    Article currentArticle;
    int articleId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_article);

        edtTitle = findViewById(R.id.edtTitle);
        edtContent = findViewById(R.id.edtContent);
        edtAuthor = findViewById(R.id.edtAuthor);
        edtThumbnail = findViewById(R.id.edtThumbnail);
        btnSave = findViewById(R.id.btnSaveArticle);

        articleId = getIntent().getIntExtra("article_id", -1);

        if (articleId != -1) {
            new Thread(() -> {
                currentArticle = DatabaseHandler.getInstance(this).getArticleDao().getById(articleId);
                runOnUiThread(() -> {
                    if (currentArticle != null) {
                        edtTitle.setText(currentArticle.getTitle());
                        edtContent.setText(currentArticle.getContent());
                        edtAuthor.setText(currentArticle.getAuthor());
                        edtThumbnail.setText(currentArticle.getThumbnailUrl());
                    }
                });
            }).start();
        }

        btnSave.setOnClickListener(v -> saveArticle());
    }

    private void saveArticle() {
        String title = edtTitle.getText().toString().trim();
        String content = edtContent.getText().toString().trim();
        String author = edtAuthor.getText().toString().trim();
        String thumbnail = edtThumbnail.getText().toString().trim();

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(content) || TextUtils.isEmpty(author) || TextUtils.isEmpty(thumbnail)) {
            Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(() -> {
            if (currentArticle == null) {
                currentArticle = new Article();
                currentArticle.setTitle(title);
                currentArticle.setContent(content);
                currentArticle.setAuthor(author);
                currentArticle.setThumbnailUrl(thumbnail);
                currentArticle.setStatus("Published");
                currentArticle.setPublishedAt(Instant.now());

                DatabaseHandler.getInstance(this).getArticleDao().insert(currentArticle);
                runOnUiThread(() -> {
                    Toast.makeText(this, "Đã thêm bài viết", Toast.LENGTH_SHORT).show();
                    finish();
                });
            } else {
                currentArticle.setTitle(title);
                currentArticle.setContent(content);
                currentArticle.setAuthor(author);
                currentArticle.setThumbnailUrl(thumbnail);
                currentArticle.setStatus("Published");

                DatabaseHandler.getInstance(this).getArticleDao().update(currentArticle);
                runOnUiThread(() -> {
                    Toast.makeText(this, "Đã cập nhật bài viết", Toast.LENGTH_SHORT).show();
                    finish();
                });
            }
        }).start();
    }
}
