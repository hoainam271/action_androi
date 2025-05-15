package com.example.newspaper.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newspaper.R;
import com.example.newspaper.models.SavedArticle;

import java.util.List;

public class SavedNewsAdapter extends RecyclerView.Adapter<SavedNewsAdapter.SavedViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(SavedArticle article);
    }

    private final List<SavedArticle> savedArticles;
    private final OnItemClickListener listener;

    public SavedNewsAdapter(List<SavedArticle> savedArticles, OnItemClickListener listener) {
        this.savedArticles = savedArticles;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SavedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_saved_article, parent, false);
        return new SavedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedViewHolder holder, int position) {
        holder.bind(savedArticles.get(position));
    }

    @Override
    public int getItemCount() {
        return savedArticles != null ? savedArticles.size() : 0;
    }

    class SavedViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTextView;
        private final TextView descriptionTextView;
        private final TextView categoryTextView;
        private final ImageView imageView;

        public SavedViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.tvTitle);
            descriptionTextView = itemView.findViewById(R.id.tvDescription);
            categoryTextView = itemView.findViewById(R.id.tvCategory);
            imageView = itemView.findViewById(R.id.ivThumbnail);
        }

        public void bind(SavedArticle article) {
            titleTextView.setText(article.getTitle());
            descriptionTextView.setText(article.getDescription());
            categoryTextView.setText(article.getCategory());

            Glide.with(itemView.getContext())
                    .load(article.getImageUrl())
                    .into(imageView);
//.placeholder(R.drawable.placeholder) // ảnh tạm nếu chưa có
            itemView.setOnClickListener(v -> listener.onItemClick(article));
        }
    }
}
