package com.example.newspaper;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.newspaper.database.DatabaseHandler;
import com.example.newspaper.database.dao.SavedArticleDao;
import com.example.newspaper.models.SavedArticle;

public class ArticleDetailActivity extends AppCompatActivity {

    private ImageView thumbnail, avatar, bookmarkIcon;
    private TextView titleView, descriptionView;

    private SavedArticleDao savedArticleDao;
    private boolean isSaved = false;
    private SavedArticle currentArticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_article_detail);

        thumbnail = findViewById(R.id.thumbnail);
        avatar = findViewById(R.id.avatar);
        bookmarkIcon = findViewById(R.id.bookmark_icon);
        titleView = findViewById(R.id.textView2);
        descriptionView = findViewById(R.id.textView9);

        String articleId = getIntent().getStringExtra("articleId");
        String title = getIntent().getStringExtra("title");
        String description = getIntent().getStringExtra("description");
        String imageUrl = getIntent().getStringExtra("imageUrl");
        String category = getIntent().getStringExtra("category");

        titleView.setText(title);
        descriptionView.setText(description);
        Glide.with(this).load(imageUrl).into(thumbnail);

        Glide.with(this)
                .load("https://cdnphoto.dantri.com.vn/dpUx3g4l-67rPhQqukF9fel8sn0=/zoom/120_120/2023/06/14/anh-1-crop-1686754741433.jpeg")
                .circleCrop()
                .into(avatar);

        findViewById(R.id.back_icon).setOnClickListener(v -> finish());

        currentArticle = new SavedArticle(
                articleId, title, description, "", imageUrl, category, System.currentTimeMillis()
        );

        savedArticleDao = DatabaseHandler.getInstance(this).getSavedArticleDao();

        // Kiểm tra bài viết đã lưu chưa dựa trên articleId
        new Thread(() -> {
            isSaved = savedArticleDao.getSavedArticleByArticleId(articleId) != null;
            runOnUiThread(() -> {
                bookmarkIcon.setImageResource(isSaved ? R.drawable.ic_bookmark_filled : R.drawable.ic_bookmark_border);
            });
        }).start();

        bookmarkIcon.setOnClickListener(v -> {
            new Thread(() -> {
                if (savedArticleDao.getSavedArticleByArticleId(articleId) != null) {
                    savedArticleDao.deleteByArticleId(articleId);
                    runOnUiThread(() -> {
                        bookmarkIcon.setImageResource(R.drawable.ic_bookmark_border);
                        Toast.makeText(this, "Đã gỡ lưu bài viết", Toast.LENGTH_SHORT).show();
                        isSaved = false;
                    });
                } else {
                    savedArticleDao.insert(currentArticle); // nếu đã tồn tại → sẽ bị IGNORE
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
