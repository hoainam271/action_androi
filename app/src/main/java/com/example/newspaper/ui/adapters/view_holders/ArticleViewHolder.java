package com.example.newspaper.ui.adapters.view_holders;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newspaper.ArticleDetailActivity;
import com.example.newspaper.R;
import com.example.newspaper.ui.adapters.models.ArticleViewItem;
import com.example.newspaper.ui.adapters.models.BaseRecycleViewItem;

public class ArticleViewHolder extends BaseViewHolder{
    public ArticleViewHolder(@NonNull View itemView) {
        super(itemView);

        itemView.setOnClickListener(v -> {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Intent intent = new Intent(itemView.getContext(), ArticleDetailActivity.class);
                itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public void onBindViewHolder(BaseRecycleViewItem baseRecycleViewItem) {
        ArticleViewItem item = (ArticleViewItem) baseRecycleViewItem;

        ImageView thumbnail = findViewById(R.id.item_image);
        TextView title = findViewById(R.id.item_title);
        TextView description = findViewById(R.id.item_description);

        title.setText(item.getTitle());
        description.setText(item.getDescription());
        Glide.with(itemView.getContext())
                .load(item.getThumbnail())
                .into(thumbnail);
    }
}
