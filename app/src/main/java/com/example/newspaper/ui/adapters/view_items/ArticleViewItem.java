package com.example.newspaper.ui.adapters.view_items;

import com.example.newspaper.pojo.ArticleWithCategory;

import lombok.Getter;

@Getter
public class ArticleViewItem {

    public ArticleViewItem(ArticleWithCategory article, TypeDisplay typeDisplay) {
        this.article = article;
        this.type = typeDisplay;
    }

    public enum TypeDisplay{
        MAIN,
        CATEGORY,
        NOTIFICATION,
        SAVED,
        HISTORY
    }
    private ArticleWithCategory article;
    private TypeDisplay type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArticleViewItem that = (ArticleViewItem) o;

        return article != null && article.equals(that.article) && type == that.type;
    }

    @Override
    public int hashCode() {
        int result = article != null ? article.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
