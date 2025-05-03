package com.example.newspaper.ui.adapters.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public abstract class BaseRecycleViewItem {
    public enum ItemTypes{
        ARTICLE,

    }

    private final String title;
    private final String description;
    private final String thumbnail;
    private final ItemTypes type;
}
