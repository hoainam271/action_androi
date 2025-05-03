package com.example.newspaper.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newspaper.R;
import com.example.newspaper.SearchActivity;
import com.example.newspaper.models.Article;
import com.example.newspaper.ui.adapters.ArticleRecycleViewAdapter;
import com.example.newspaper.ui.adapters.models.ArticleViewItem;
import com.example.newspaper.ui.adapters.models.BaseRecycleViewItem;

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
        List<BaseRecycleViewItem> items = new ArrayList<>();

        navigateSearchPage(view);

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

        articles.forEach(article -> {
            items.add(new ArticleViewItem(article));
        });
        ArticleRecycleViewAdapter adapter = new ArticleRecycleViewAdapter(items);
        recyclerView.setAdapter(adapter);
    }

    public void navigateSearchPage(View view){
        LinearLayout searchBox = view.findViewById(R.id.search_box);
        searchBox.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), SearchActivity.class);
            startActivity(intent);
        });
    }
}
