package com.example.newspaper.ui.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newspaper.LoginActivity;
import com.example.newspaper.R;
import com.example.newspaper.SearchActivity;
import com.example.newspaper.UpdateInforActivity;
import com.example.newspaper.database.DatabaseHandler;
import com.example.newspaper.database.repositories.ArticleRepository;
import com.example.newspaper.models.Notification;
import com.example.newspaper.ui.adapters.ArticleRecycleViewAdapter;
import com.example.newspaper.ui.adapters.view_items.ArticleViewItem;
import com.example.newspaper.ui.adapters.view_models.ArticleViewModel;

import java.util.ArrayList;
import java.util.List;

public class FragmentHome extends Fragment {
    ArticleViewModel articleViewModel;
    ArticleRecycleViewAdapter adapter;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DatabaseHandler db = DatabaseHandler.getInstance(requireContext());

        recyclerView = view.findViewById(R.id.list_blogs);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ArticleRecycleViewAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        SharedPreferences prefs = requireContext().getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);

        if(isLoggedIn){
            navigate(view, R.id.accountBtn, UpdateInforActivity.class);
            navigate(view, R.id.notification_icon, Notification.class);
        }else{
            navigate(view, R.id.accountBtn, LoginActivity.class);
        }
        navigate(view, R.id.accountBtn, LoginActivity.class);
        navigate(view, R.id.search_box, SearchActivity.class);

        ArticleRepository repository = new ArticleRepository(requireContext());
        articleViewModel = new ArticleViewModel(repository);

        articleViewModel.loadArticles(10, 0);
        articleViewModel.getArticles().observe(getViewLifecycleOwner(), articles -> {
            List<ArticleViewItem> items = new ArrayList<>();
            articles.forEach(a -> items.add(new ArticleViewItem(a, ArticleViewItem.TypeDisplay.MAIN)));
            adapter.setArticles(items);
        });
        // ✅ Mở trang Admin
        view.findViewById(R.id.btnAdmin).setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), com.example.newspaper.ui.activities.AdminHomeActivity.class);
            startActivity(intent);
        });

    }

    public void navigate(View parentView, int viewId, Class<?> targetActivity) {
        View button = parentView.findViewById(viewId);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), targetActivity);
            startActivity(intent);
        });
    }
}
