package com.example.newspaper.ui.adapters.view_items;

import com.example.newspaper.models.Article;

import java.util.List;

import lombok.Getter;

@Getter
public class ArticleViewItem {

    public ArticleViewItem(Article article, TypeDisplay typeDisplay) {
        this.article = article;
        this.type = typeDisplay;
    }

    public enum TypeDisplay{
        MAIN,
        CATEGORY
    }
    private Article article;
    private TypeDisplay type;
}
