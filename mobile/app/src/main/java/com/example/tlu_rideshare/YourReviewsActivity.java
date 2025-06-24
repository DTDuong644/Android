package com.example.tlu_rideshare;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class YourReviewsActivity extends AppCompatActivity {

    private ImageButton backButtonReviews;
    private TextView totalReviewsCount;
    private RecyclerView yourReviewsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_your_reviews);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.your_reviews_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        backButtonReviews = findViewById(R.id.back_button_reviews);
        totalReviewsCount = findViewById(R.id.total_reviews_count);
        yourReviewsRecyclerView = findViewById(R.id.your_reviews_recycler_view);

        // Set up click listener for back button
        backButtonReviews.setOnClickListener(v -> finish());

        // Set up RecyclerView adapter and layout manager
        yourReviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<YourReviewItem> sampleData = new ArrayList<>();
        sampleData.add(new YourReviewItem("Tài xế: Nguyễn Văn A", "Đại học Thủy Lợi => Bến xe Mỹ Đình", "1/5", "Rất không hài lòng"));
        sampleData.add(new YourReviewItem("Tài xế: Lê Thị C", "Đại học Thủy Lợi => Bến xe Nam Thanh Hóa", "2/5", "Không hài lòng"));
        sampleData.add(new YourReviewItem("Tài xế: Nguyễn Tiến B", "Đại học Thủy Lợi => Bến xe Bắc Vinh", "3/5", "Bình thường"));
        sampleData.add(new YourReviewItem("Tài xế: Tô Đức D", "Đại học Thủy Lợi => Bến xe Vĩnh Yên", "4/5", "Hài lòng"));
        sampleData.add(new YourReviewItem("Tài xế: Trần Văn E", "Đại học Thủy Lợi => Bến xe Giáp Bát", "5/5", "Rất hài lòng"));

        YourReviewsAdapter adapter = new YourReviewsAdapter(sampleData);
        yourReviewsRecyclerView.setAdapter(adapter);

        // Update total reviews count
        totalReviewsCount.setText(String.valueOf(sampleData.size()));
    }
}