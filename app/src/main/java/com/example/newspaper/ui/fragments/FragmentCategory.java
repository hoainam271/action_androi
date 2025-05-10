package com.example.newspaper.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newspaper.R;
import com.example.newspaper.models.Article;
import com.example.newspaper.ui.adapters.ArticleRecycleViewAdapter;
import com.example.newspaper.ui.adapters.view_items.ArticleViewItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class FragmentCategory extends Fragment {
    private RecyclerView newsRecyclerView;
//    private NewsAdapter newsAdapter;
    private TabLayout tabLayout;
    private ImageButton menuButton;
    private BottomSheetDialog categoriesDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Khởi tạo RecyclerView
        newsRecyclerView = view.findViewById(R.id.newsRecyclerView);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Tạo dữ liệu tin tức mẫu
        List<Article> newsList = createSampleNews();
        List<ArticleViewItem> items = new ArrayList<>();

        for (Article a : newsList){
            System.out.println(a.getPublishedAt());
            items.add(new ArticleViewItem(a, ArticleViewItem.TypeDisplay.CATEGORY));
        }

        ArticleRecycleViewAdapter adapter = new ArticleRecycleViewAdapter(items);
        newsRecyclerView.setAdapter(adapter);

        tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Xử lý khi tab được chọn
                // Trong ứng dụng thực tế, bạn sẽ cần tải dữ liệu tương ứng
                loadNewsByCategory(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Không làm gì
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Không làm gì
            }
        });

        // Thêm xử lý cho nút menu
        menuButton = view.findViewById(R.id.menuButton);
        if (menuButton != null) {
            menuButton.setOnClickListener(v -> showCategoriesDialog());
        }

        // Khởi tạo categories dialog
        setupCategoriesDialog();
    }

    private void setupCategoriesDialog() {
        categoriesDialog = new BottomSheetDialog(requireContext());
        View dialogView = getLayoutInflater().inflate(R.layout.menu_categories, null);
        categoriesDialog.setContentView(dialogView);

        // Tìm và thiết lập sự kiện cho nút đóng
        ImageButton closeButton = dialogView.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(v -> categoriesDialog.dismiss());

        // Thiết lập sự kiện click cho các chuyên mục
        setupCategoryClickListeners(dialogView);
    }

    private void setupCategoryClickListeners(View dialogView) {
        // Chuyên mục Tin mới nhất
        View latestNewsCategory = dialogView.findViewById(R.id.latestNewsCategory);
        latestNewsCategory.setOnClickListener(v -> {
            tabLayout.getTabAt(0).select(); // Chọn tab "Mới nhất"
            categoriesDialog.dismiss();
        });

        // Chuyên mục Điểm tin nổi bật
        View featuredNewsCategory = dialogView.findViewById(R.id.featuredNewsCategory);
        featuredNewsCategory.setOnClickListener(v -> {
            tabLayout.getTabAt(1).select(); // Chọn tab "Điểm tin"
            categoriesDialog.dismiss();
        });

        // Chuyên mục Kinh doanh
        View businessCategory = dialogView.findViewById(R.id.businessCategory);
        businessCategory.setOnClickListener(v -> {
            tabLayout.getTabAt(3).select(); // Chọn tab "Kinh doanh"
            categoriesDialog.dismiss();
        });

        // Thêm listener cho các chuyên mục khác
        setupMoreCategories(dialogView);
    }

    private void setupMoreCategories(View dialogView) {
        // Xã hội
        View socialCategory = dialogView.findViewById(R.id.socialCategory);
        if (socialCategory != null) {
            socialCategory.setOnClickListener(v -> {
                // Có thể thêm tab mới hoặc chuyển đến Activity khác
                loadNewsByCategory("social");
                categoriesDialog.dismiss();
            });
        }

        // Thế giới
        View worldCategory = dialogView.findViewById(R.id.worldCategory);
        if (worldCategory != null) {
            worldCategory.setOnClickListener(v -> {
                loadNewsByCategory("world");
                categoriesDialog.dismiss();
            });
        }

        // Giải trí
        View entertainmentCategory = dialogView.findViewById(R.id.entertainmentCategory);
        if (entertainmentCategory != null) {
            entertainmentCategory.setOnClickListener(v -> {
                loadNewsByCategory("entertainment");
                categoriesDialog.dismiss();
            });
        }

        // Bất động sản
        View realEstateCategory = dialogView.findViewById(R.id.realEstateCategory);
        if (realEstateCategory != null) {
            realEstateCategory.setOnClickListener(v -> {
                loadNewsByCategory("realestate");
                categoriesDialog.dismiss();
            });
        }
    }

    private void showCategoriesDialog() {
        if (categoriesDialog != null) {
            categoriesDialog.show();
        }
    }

    private void loadNewsByCategory(int categoryPosition) {
        // Xử lý tải dữ liệu theo tab đã chọn
        String category;

        switch (categoryPosition) {
            case 0:
                category = "latest";
                break;
            case 1:
                category = "featured";
                break;
            case 2:
                category = "personal";
                break;
            case 3:
                category = "business";
                break;
            default:
                category = "latest";
        }

        loadNewsByCategory(category);
    }

    private void loadNewsByCategory(String category) {
//        List<NewsItem> filteredNews = new ArrayList<>();
//
//        if (category.equals("latest")) {
//            filteredNews = createSampleNews(); // Lấy tất cả tin
//        } else {
//            for (NewsItem item : createSampleNews()) {
//                if ((category.equals("social") && item.getCategory().equals("Xã hội")) ||
//                        (category.equals("world") && item.getCategory().equals("Thế giới")) ||
//                        (category.equals("law") && item.getCategory().equals("Pháp luật"))) {
//                    filteredNews.add(item);
//                }
//            }
//        }
//
//        newsAdapter.updateNewsList(filteredNews);
    }

    private List<Article> createSampleNews() {
        List<Article> newsList = new ArrayList<>();

        newsList.add(Article.builder()
                        .title("Ba mẹ con tử vong trong căn nhà khóa cửa")
                        .summary("(Dân trí) - Ba mẹ con ở tỉnh Gia Lai được phát hiện đã tử vong trong căn nhà khóa cửa. Công an đang điều tra nguyên nhân vụ việc.")
                        .category("Pháp luật")
                        .publishedAt(Instant.now().minus(30, ChronoUnit.SECONDS))
                .build());

        newsList.add(Article.builder()
                .title("Ukraine tuyên bố lần đầu bắn hạ tiêm kích Nga từ xuồng không người lái")
                .summary("(Dân trí) - Ukraine cho biết đã bắn rơi tiêm kích Nga trên biển từ xuồng không người lái.")
                .category("Thế giới")
                .publishedAt(Instant.now().minus(15, ChronoUnit.MINUTES))
                .build());

        newsList.add(Article.builder()
                .title("Ô tô chở 20 người gặp tai nạn trên quốc lộ")
                .summary("(Dân trí) - Xe tải đang lưu thông trên quốc lộ 20 ở Lâm Đồng, bất ngờ va chạm ô tô chở khách. Vụ tai nạn làm nhiều người hoảng loạn, giao thông qua khu vực bị ách tắc.")
                .category("Xã hội")
                .publishedAt(Instant.now().minus(30, ChronoUnit.MINUTES))
                .build());

        newsList.add(Article.builder()
                .title("Vụ 3 người tử vong trong khách sạn ở Nha Trang: Người phụ nữ đang mang thai")
                .summary("(Dân trí) - Gia đình đã chôn cất 2 mẹ con trong vụ 3 người tử vong tại khách sạn ở Nha Trang. Theo người thân của nữ nạn nhân, người này đang mang thai hơn 3 tháng.")
                .category("Xã hội")
                .publishedAt(Instant.now().minus(90, ChronoUnit.MINUTES))
                .build());
        return newsList;
    }
}
