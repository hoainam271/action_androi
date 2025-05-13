package com.example.newspaper;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newspaper.database.repositories.ArticleRepository;
import com.example.newspaper.database.repositories.SearchHistoryRepository;
import com.example.newspaper.models.SearchHistory;
import com.example.newspaper.ui.adapters.SearchHistoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    SearchHistoryRepository repository;
    ArticleRepository articleRepository;

    private List<String> trends;
    private List<SearchHistory> histories;
    private ListView listTrends;
    private RecyclerView listHistories;
    private EditText textKeyword;

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
        mapping();

        histories = repository.getSearchHistory(1);
        trends = repository.getSearchTrends();

        ArrayAdapter<String> trendAdapter = new ArrayAdapter<>(this, R.layout.item_trend_search_view, R.id.trend_topic, trends);
        SearchHistoryAdapter historyAdapter = new SearchHistoryAdapter(histories);

        listTrends.setAdapter(trendAdapter);
        listHistories.setAdapter(historyAdapter);

        textKeyword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                        (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {
                    String input = textKeyword.getText().toString().trim();

                    return true;
                }
                return false;
            }
        });
    }

    public void mapping(){
        listTrends = findViewById(R.id.list_trend);
        listHistories = findViewById(R.id.list_history);
        textKeyword = findViewById(R.id.textKeyword);

        repository = new SearchHistoryRepository(this);
        articleRepository = new ArticleRepository(this);
    }
}