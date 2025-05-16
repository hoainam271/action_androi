package com.example.newspaper.ui.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.newspaper.R;
import com.example.newspaper.database.DatabaseHandler;
import com.example.newspaper.models.Article;

import java.time.Instant;

import jp.wasabeef.richeditor.RichEditor;

public class AddEditArticleActivity extends AppCompatActivity {

    EditText edtTitle, edtAuthor, edtThumbnail, edtSummary;
    RichEditor editorContent;
    Button btnSave;
    ImageView imgPreview;
    Article currentArticle;
    int articleId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_article);

        edtTitle = findViewById(R.id.edtTitle);
        edtAuthor = findViewById(R.id.edtAuthor);
        edtThumbnail = findViewById(R.id.edtThumbnail);
        edtSummary = findViewById(R.id.edtSummary);
        editorContent = findViewById(R.id.editorContent);
        btnSave = findViewById(R.id.btnSaveArticle);
        imgPreview = findViewById(R.id.imgPreview);

        editorContent.setEditorHeight(200);
        editorContent.setPlaceholder("Nhập nội dung bài viết...");

        edtThumbnail.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void afterTextChanged(Editable s) {
                String url = s.toString().trim();
                if (!url.isEmpty()) {
                    Glide.with(AddEditArticleActivity.this)
                            .load(url)
                            .into(imgPreview);
                } else {
                    imgPreview.setImageDrawable(null);
                }
            }
        });

        articleId = getIntent().getIntExtra("article_id", -1);
        if (articleId != -1) {
            new Thread(() -> {
                currentArticle = DatabaseHandler.getInstance(this).getArticleDao().getById(articleId);
                runOnUiThread(() -> {
                    if (currentArticle != null) {
                        edtTitle.setText(currentArticle.getTitle());
                        edtAuthor.setText(currentArticle.getAuthor());
                        edtThumbnail.setText(currentArticle.getThumbnailUrl());
                        edtSummary.setText(currentArticle.getSummary());
                        editorContent.setHtml(currentArticle.getContent());
                    }
                });
            }).start();
        }

        btnSave.setOnClickListener(v -> saveArticle());
    }

    private void saveArticle() {
        String title = edtTitle.getText().toString().trim();
        String author = edtAuthor.getText().toString().trim();
        String thumbnail = edtThumbnail.getText().toString().trim();
        String summary = edtSummary.getText().toString().trim();
        String content = editorContent.getHtml();

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(author) ||
                TextUtils.isEmpty(thumbnail) || TextUtils.isEmpty(content)) {
            Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(() -> {
            if (currentArticle == null) {
                currentArticle = new Article();
                currentArticle.setTitle(title);
                currentArticle.setAuthor(author);
                currentArticle.setThumbnailUrl(thumbnail);
                currentArticle.setSummary(summary);
                currentArticle.setContent(content);
                currentArticle.setStatus("Published");
                currentArticle.setPublishedAt(Instant.now());
                currentArticle.setCategoryId(1);
                currentArticle.setUserId(1);

                long insertedId = DatabaseHandler.getInstance(this).getArticleDao().insert(currentArticle);
                currentArticle.setId((int) insertedId);

                runOnUiThread(() -> {
                    Toast.makeText(this, "Đã thêm bài viết", Toast.LENGTH_SHORT).show();
                    finish();
                });
            } else {
                currentArticle.setTitle(title);
                currentArticle.setAuthor(author);
                currentArticle.setThumbnailUrl(thumbnail);
                currentArticle.setSummary(summary);
                currentArticle.setContent(content);
                currentArticle.setUpdatedAt(Instant.now());

                DatabaseHandler.getInstance(this).getArticleDao().update(currentArticle);

                runOnUiThread(() -> {
                    Toast.makeText(this, "Đã cập nhật bài viết", Toast.LENGTH_SHORT).show();
                    finish();
                });
            }
        }).start();
    }
}
