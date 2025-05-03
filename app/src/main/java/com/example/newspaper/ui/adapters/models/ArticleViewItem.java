package com.example.newspaper.ui.adapters.models;

import com.example.newspaper.models.Article;

public class ArticleViewItem extends BaseRecycleViewItem{

    private Article item;
    public ArticleViewItem(Article article) {
        super(article.getTitle(), article.getSummary(), article.getThumbnailUrl(), ItemTypes.ARTICLE);

        this.item = article;
    }
}
