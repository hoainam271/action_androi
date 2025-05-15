package com.example.newspaper.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newspaper.R;
import com.example.newspaper.models.Article;

import java.util.List;
import java.util.function.Consumer;

public class ArticleAdminAdapter extends RecyclerView.Adapter<ArticleAdminAdapter.ViewHolder> {

    private List<Article> articles;
    private Consumer<Article> onEdit;
    private Consumer<Article> onDelete;

    public ArticleAdminAdapter(List<Article> articles, Consumer<Article> onEdit, Consumer<Article> onDelete) {
        this.articles = articles;
        this.onEdit = onEdit;
        this.onDelete = onDelete;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvStatus;
        Button btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    @NonNull
    @Override
    public ArticleAdminAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleAdminAdapter.ViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.tvTitle.setText(article.getTitle());
        holder.tvStatus.setText("Trạng thái: " + article.getStatus());

        holder.btnEdit.setOnClickListener(v -> onEdit.accept(article));
        holder.btnDelete.setOnClickListener(v -> onDelete.accept(article));
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }
}
