package com.example.newspaper.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newspaper.R;
import com.example.newspaper.ui.activities.ManageArticlesActivity;

public class AdminHomeActivity extends AppCompatActivity {

    Button btnManageArticles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        btnManageArticles = findViewById(R.id.btnManageArticles);

        btnManageArticles.setOnClickListener(v -> {
            Intent intent = new Intent(this, ManageArticlesActivity.class);
            startActivity(intent);
        });
    }
}
