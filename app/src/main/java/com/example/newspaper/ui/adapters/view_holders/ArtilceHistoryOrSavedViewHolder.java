package com.example.newspaper.ui.adapters.view_holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.newspaper.R;
import com.example.newspaper.ui.adapters.view_items.ArticleViewItem;
import com.example.newspaper.utils.DateTimeFormatterUtil;

import java.time.Instant;

public class ArtilceHistoryOrSavedViewHolder extends BaseViewHolder<ArticleViewItem> {

    private final TextView title, category, savedAt;
    private final ImageView thumbnail;

    public ArtilceHistoryOrSavedViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.title);
        category = itemView.findViewById(R.id.category);
        savedAt = itemView.findViewById(R.id.saved_or_read_at);
        thumbnail = itemView.findViewById(R.id.thumbnail);
    }

    @Override
    public void onBindViewHolder(ArticleViewItem item) {
        title.setText(item.getArticle().article.getTitle());
        category.setText(item.getArticle().category.getName());

        // Format thời gian bài viết
        DateTimeFormatterUtil formatter = new DateTimeFormatterUtil();
        Instant publishedAt = item.getArticle().article.getPublishedAt();
        String time = publishedAt != null ? formatter.format(publishedAt) : "Không rõ thời gian";

        savedAt.setText("Đã lưu " + time);

        Glide.with(itemView.getContext())
                .load(item.getArticle().article.getThumbnailUrl())
                .centerCrop()
                .into(thumbnail);
    }
}
