package com.example.newspaper.ui.adapters.view_holders;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.newspaper.NewsDetailActivity;
import com.example.newspaper.R;
import com.example.newspaper.models.Article;
import com.example.newspaper.pojo.ArticleWithCategory;
import com.example.newspaper.ui.adapters.view_items.ArticleViewItem;

public class ArticleViewHolder extends BaseViewHolder<ArticleViewItem> {

    private final ImageView thumbnail;
    private final TextView title;
    private final TextView description;

    public ArticleViewHolder(@NonNull View itemView) {
        super(itemView);

        thumbnail = itemView.findViewById(R.id.item_image);
        title = itemView.findViewById(R.id.item_title);
        description = itemView.findViewById(R.id.item_description);
    }

    @Override
    public void onBindViewHolder(ArticleViewItem item) {
        ArticleWithCategory awc = item.getArticle();  // dùng getter thay vì item.article
        Article article = awc.getArticle();

        title.setText(article.getTitle());
        description.setText(article.getSummary());

        Glide.with(itemView.getContext())
                .load(article.getThumbnailUrl())
                .apply(new RequestOptions().transform(new RoundedCorners(15)))
                .placeholder(R.drawable.ic_launcher_background)
                .into(thumbnail);

        itemView.setOnClickListener(v -> {
            Intent intent = new Intent(itemView.getContext(), NewsDetailActivity.class);
            intent.putExtra("articleId", article.getId());
            intent.putExtra("title", article.getTitle());
            intent.putExtra("description", article.getSummary());
            intent.putExtra("imageUrl", article.getThumbnailUrl());
            intent.putExtra("category", awc.getCategory().getName());
            intent.putExtra("content", article.getContent());
            itemView.getContext().startActivity(intent);
        });
    }
}
