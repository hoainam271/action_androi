package com.example.newspaper;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.newspaper.database.DatabaseHandler;
import com.example.newspaper.database.dao.SavedArticleDao;
import com.example.newspaper.models.SavedArticle;

public class NewsDetailActivity extends AppCompatActivity {

    private TextView titleTextView, descriptionTextView;
    private ImageView newsImageView, bookmarkIcon;

    private DatabaseHandler db;
    private SavedArticleDao savedArticleDao;
    private boolean isSaved = false;

    private String articleId = "";
    private String newsTitle = "";
    private String newsDesc = "";
    private String newsImageUrl = "";
    private String category = "";
    private String content = "";

    private SavedArticle currentSavedArticle = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        titleTextView = findViewById(R.id.detailTitle);
        descriptionTextView = findViewById(R.id.detailDesc);
        newsImageView = findViewById(R.id.detailImage);
        bookmarkIcon = findViewById(R.id.bookmarkIcon);

        // Nhận dữ liệu từ Intent
        articleId = getIntent().getStringExtra("articleId");
        newsTitle = getIntent().getStringExtra("title");
        newsDesc = getIntent().getStringExtra("description");
        newsImageUrl = getIntent().getStringExtra("imageUrl");
        category = getIntent().getStringExtra("category");
        content = getIntent().getStringExtra("content");

        titleTextView.setText(newsTitle);
        descriptionTextView.setText(newsDesc);
        Glide.with(this).load(newsImageUrl).into(newsImageView);

        db = DatabaseHandler.getInstance(this);
        savedArticleDao = db.getSavedArticleDao();

        new Thread(() -> {
            currentSavedArticle = savedArticleDao.getSavedArticleByArticleId(articleId);
            runOnUiThread(() -> {
                if (currentSavedArticle != null) {
                    isSaved = true;
                    bookmarkIcon.setImageResource(R.drawable.ic_bookmark_filled);
                } else {
                    isSaved = false;
                    bookmarkIcon.setImageResource(R.drawable.ic_bookmark_border);
                }
            });
        }).start();

        bookmarkIcon.setOnClickListener(v -> {
            new Thread(() -> {
                if (isSaved) {
                    savedArticleDao.deleteByArticleId(articleId);
                    runOnUiThread(() -> {
                        bookmarkIcon.setImageResource(R.drawable.ic_bookmark_border);
                        Toast.makeText(this, "Đã gỡ lưu", Toast.LENGTH_SHORT).show();
                        isSaved = false;
                        currentSavedArticle = null;
                    });
                } else {
                    long timestamp = System.currentTimeMillis();
                    currentSavedArticle = new SavedArticle(
                            articleId,
                            newsTitle,
                            newsDesc,
                            content != null ? content : "",
                            newsImageUrl != null ? newsImageUrl : "",
                            category != null ? category : "",
                            timestamp
                    );
                    savedArticleDao.insert(currentSavedArticle);
                    runOnUiThread(() -> {
                        bookmarkIcon.setImageResource(R.drawable.ic_bookmark_filled);
                        Toast.makeText(this, "Đã lưu bài viết", Toast.LENGTH_SHORT).show();
                        isSaved = true;
                    });
                }
            }).start();
        });
    }
}
