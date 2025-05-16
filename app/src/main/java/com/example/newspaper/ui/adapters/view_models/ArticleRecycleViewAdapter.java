package com.example.newspaper.ui.adapters.view_models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newspaper.R;
import com.example.newspaper.models.Article;

import java.util.List;
import java.util.function.Consumer;

public class ArticleRecycleViewAdapter extends RecyclerView.Adapter<ArticleRecycleViewAdapter.ArticleViewHolder> {

    private List<Article> articles;
    private Consumer<Article> onEdit, onDelete;

    public ArticleRecycleViewAdapter(List<Article> articles, Consumer<Article> onEdit, Consumer<Article> onDelete) {
        this.articles = articles;
        this.onEdit = onEdit;
        this.onDelete = onDelete;
    }

    public static class ArticleViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvStatus;
        ImageView imgThumbnail;
        Button btnEdit, btnDelete;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            imgThumbnail = itemView.findViewById(R.id.imgThumbnail);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.tvTitle.setText(article.getTitle());
        holder.tvStatus.setText("Trạng thái: " + article.getStatus());

        Glide.with(holder.itemView.getContext())
                .load(article.getThumbnailUrl())
                .placeholder(R.drawable.bg_image_placeholder)
                .error(R.drawable.bg_image_placeholder)
                .into(holder.imgThumbnail);

        holder.btnEdit.setOnClickListener(v -> onEdit.accept(article));
        holder.btnDelete.setOnClickListener(v -> onDelete.accept(article));
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void setArticles(List<Article> updatedArticles) {
        this.articles = updatedArticles;
        notifyDataSetChanged();
    }
}
