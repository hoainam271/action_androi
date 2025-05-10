package com.example.newspaper.ui.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.example.newspaper.models.Article;
import com.example.newspaper.ui.adapters.ArticleRecycleViewAdapter;
import com.example.newspaper.ui.adapters.view_items.ArticleViewItem;

import java.util.ArrayList;
import java.util.List;

public class FragmentHome extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.list_blogs);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<Article> articles = new ArrayList<>();
        List<ArticleViewItem> items = new ArrayList<>();

        navigate(view, R.id.search_box, SearchActivity.class);

        SharedPreferences prefs = requireContext().getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);

        if(isLoggedIn){
            navigate(view, R.id.accountBtn, UpdateInforActivity.class);
        }else{
            navigate(view, R.id.accountBtn, LoginActivity.class);
        }

        navigate(view, R.id.accountBtn, LoginActivity.class);

        articles.add(Article.builder()
                        .title("Viện KSND Tối cao nhận định về vụ tai nạn dẫn đến nổ súng ở Vĩnh Long")
                        .summary("Ngày 3.5, Viện KSND Tối cao cho biết, thực hiện ý kiến chỉ đạo của Viện trưởng Viện KSND tối cao, các đơn vị nghiệp vụ Viện KSND Tối cao đã kiểm tra lại quyết định giải quyết khiếu nại số 55/QĐ-VKS ngày 14.3.2025 đã có hiệu lực pháp luật của Viện KSND tỉnh Vĩnh Long.")
                        .thumbnailUrl("https://images2.thanhnien.vn/528068263637045248/2025/5/3/edit-3-1746246222496141030607.jpeg")
                .build());

        articles.add(Article.builder()
                .title("Viện KSND Tối cao nhận định về vụ tai nạn dẫn đến nổ súng ở Vĩnh Long")
                .summary("Ngày 3.5, Viện KSND Tối cao cho biết, thực hiện ý kiến chỉ đạo của Viện trưởng Viện KSND tối cao, các đơn vị nghiệp vụ Viện KSND Tối cao đã kiểm tra lại quyết định giải quyết khiếu nại số 55/QĐ-VKS ngày 14.3.2025 đã có hiệu lực pháp luật của Viện KSND tỉnh Vĩnh Long.")
                .thumbnailUrl("https://images2.thanhnien.vn/528068263637045248/2025/5/3/edit-3-1746246222496141030607.jpeg")
                .build());

        articles.add(Article.builder()
                .title("Viện KSND Tối cao nhận định về vụ tai nạn dẫn đến nổ súng ở Vĩnh Long")
                .summary("Ngày 3.5, Viện KSND Tối cao cho biết, thực hiện ý kiến chỉ đạo của Viện trưởng Viện KSND tối cao, các đơn vị nghiệp vụ Viện KSND Tối cao đã kiểm tra lại quyết định giải quyết khiếu nại số 55/QĐ-VKS ngày 14.3.2025 đã có hiệu lực pháp luật của Viện KSND tỉnh Vĩnh Long.")
                .thumbnailUrl("https://images2.thanhnien.vn/528068263637045248/2025/5/3/edit-3-1746246222496141030607.jpeg")
                .build());

        articles.add(Article.builder()
                .title("Viện KSND Tối cao nhận định về vụ tai nạn dẫn đến nổ súng ở Vĩnh Long")
                .summary("Ngày 3.5, Viện KSND Tối cao cho biết, thực hiện ý kiến chỉ đạo của Viện trưởng Viện KSND tối cao, các đơn vị nghiệp vụ Viện KSND Tối cao đã kiểm tra lại quyết định giải quyết khiếu nại số 55/QĐ-VKS ngày 14.3.2025 đã có hiệu lực pháp luật của Viện KSND tỉnh Vĩnh Long.")
                .thumbnailUrl("https://images2.thanhnien.vn/528068263637045248/2025/5/3/edit-3-1746246222496141030607.jpeg")
                .build());

        for(Article a : articles){
            items.add(new ArticleViewItem(a, ArticleViewItem.TypeDisplay.MAIN));
        }
        ArticleRecycleViewAdapter adapter = new ArticleRecycleViewAdapter(items);
        recyclerView.setAdapter(adapter);
    }

    public void navigate(View parentView, int viewId, Class<?> targetActivity) {
        View button = parentView.findViewById(viewId);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), targetActivity);
            startActivity(intent);
        });
    }
}
