package com.example.newspaper.ui.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newspaper.ArticleDetailActivity;
import com.example.newspaper.R;
import com.example.newspaper.pojo.ArticleWithCategory;
import com.example.newspaper.ui.adapters.view_holders.ArticleCategoryViewHolder;
import com.example.newspaper.ui.adapters.view_holders.ArticleViewHolder;
import com.example.newspaper.ui.adapters.view_holders.BaseViewHolder;
import com.example.newspaper.ui.adapters.view_holders.NotificationViewHolder;
import com.example.newspaper.ui.adapters.view_items.ArticleViewItem;

import java.util.ArrayList;
import java.util.List;

public class ArticleRecycleViewAdapter extends BaseRecycleViewAdapter<ArticleViewItem> {
    private List<ArticleViewItem> items;
    private boolean isLoading = false;

    public ArticleRecycleViewAdapter(List<ArticleViewItem> items) {
        super(items);
        this.items = items;
    }

    public void setArticles(List<ArticleViewItem> articles) {
        this.items = articles;
        notifyDataSetChanged();
    }
    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType().ordinal();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ArticleViewItem.TypeDisplay type = ArticleViewItem.TypeDisplay.values()[viewType];

        switch (type) {
            case MAIN:
                return new ArticleViewHolder(inflater.inflate(R.layout.item_blog_view, parent, false));
            case CATEGORY:
                return new ArticleCategoryViewHolder(inflater.inflate(R.layout.item_article_category_view, parent, false));
            case NOTIFICATION:
                return new NotificationViewHolder(inflater.inflate(R.layout.item_notification_view, parent, false));
            default:
                throw new IllegalArgumentException("Unknown view type: " + viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        ArticleViewItem item = items.get(position);

        holder.itemView.setOnClickListener(v -> {
            ArticleWithCategory article = item.getArticle();

            Intent intent = new Intent(v.getContext(), ArticleDetailActivity.class);
            intent.putExtra("articleId", article.article.getId() + ""); // ép int sang String
            intent.putExtra("title", article.article.getTitle());
            intent.putExtra("description", article.article.getSummary());
            intent.putExtra("imageUrl", article.article.getThumbnailUrl());
            intent.putExtra("category", article.category.getName());

            v.getContext().startActivity(intent);
        });

        holder.onBindViewHolder(item);
    }


    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public void setLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

    public void setOnScrollListener(RecyclerView recyclerView, final int pageSize, final int totalCount) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null) {
                    int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                    if (!isLoading && lastVisibleItemPosition == getItemCount() - 1 && getItemCount() < totalCount) {
                        loadNextPage(pageSize);
                    }
                }
            }
        });
    }

    private void loadNextPage(int pageSize) {
        // Tải thêm dữ liệu, ví dụ qua API hoặc từ database
    }
}
