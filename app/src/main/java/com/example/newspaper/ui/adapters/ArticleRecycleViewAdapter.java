package com.example.newspaper.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.newspaper.R;
import com.example.newspaper.ui.adapters.models.BaseRecycleViewItem;
import com.example.newspaper.ui.adapters.view_holders.ArticleViewHolder;
import com.example.newspaper.ui.adapters.view_holders.BaseViewHolder;

import java.util.List;

public class ArticleRecycleViewAdapter extends BaseRecycleViewAdapter {
    public ArticleRecycleViewAdapter(List<BaseRecycleViewItem> items) {
        super(items);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BaseRecycleViewItem.ItemTypes type = BaseRecycleViewItem.ItemTypes.values()[viewType];

        switch (type){
            case ARTICLE:
                return new ArticleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_blog_view, parent, false));
            default:
                return null;
        }
    }
}
