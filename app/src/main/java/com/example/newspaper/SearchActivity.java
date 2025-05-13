package com.example.newspaper;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.newspaper.database.repositories.SearchHistoryRepository;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    SearchHistoryRepository repository;

    private List<String> trends;
    private List<String> histories;
    private ListView listTrends, listHistories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);

        ImageView searchBox = findViewById(R.id.back_btn);
        searchBox.setOnClickListener(v -> {
            finish();
        });

        this.trends = new ArrayList<>();
        this.histories = new ArrayList<>();
        listTrends = findViewById(R.id.list_trend);
        listHistories = findViewById(R.id.list_history);

        histories = repository.getSearchHistory(1);

        ArrayAdapter<String> trendAdapter = new ArrayAdapter<>(this, R.layout.item_trend_search_view, R.id.trend_topic, trends);
        ArrayAdapter<String> historyAdapter = new ArrayAdapter<>(this, R.layout.item_history_search_view, R.id.history_topic, histories);

        listTrends.setAdapter(trendAdapter);
        listHistories.setAdapter(historyAdapter);
    }

    void fakeData(){
        this.trends.add("Trending number 1");
        this.trends.add("Trending number 2");
        this.trends.add("Trending number 3");
        this.trends.add("Trending number 4");
        this.trends.add("Trending number 5");
        this.trends.add("Trending number 6");
        this.trends.add("Trending number 7");
        this.trends.add("Trending number 8");
        this.trends.add("Trending number 9");
    }

}