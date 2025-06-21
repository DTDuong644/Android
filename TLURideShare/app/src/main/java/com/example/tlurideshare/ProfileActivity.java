package com.example.tlurideshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProfileActivity extends AppCompatActivity {

    private ImageView profileImage;
    private TextView userNameText;
    private TextView userStatusText;
    private Button editProfileButton;
    private TextView travelHistoryItem;
    private TextView yourReviewsItem;
    private TextView accountStatusItem;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.profile_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ view
        profileImage = findViewById(R.id.profile_image);
        userNameText = findViewById(R.id.user_name_text);
        userStatusText = findViewById(R.id.user_status_text);
        editProfileButton = findViewById(R.id.edit_profile_button);
        travelHistoryItem = findViewById(R.id.travel_history_item);
        yourReviewsItem = findViewById(R.id.your_reviews_item);
        accountStatusItem = findViewById(R.id.account_status_item);
        logoutButton = findViewById(R.id.logout_button);

        // Chuyển sang EditProfileActivity
        editProfileButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
            startActivity(intent);
        });

        // Chuyển sang AverageRatingActivity
        travelHistoryItem.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, AverageRatingActivity.class);
            startActivity(intent);
        });

        // Chuyển sang YourReviewsActivity
        yourReviewsItem.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, YourReviewsActivity.class);
            startActivity(intent);
        });

        // Chuyển sang AccountStatusActivity
        accountStatusItem.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, AccountStatusActivity.class);
            startActivity(intent);
        });

        // Xử lý nút đăng xuất
        logoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish(); // Đóng ProfileActivity
        });

        // TODO: Gán dữ liệu người dùng nếu có (tên, ảnh, trạng thái)
        // userNameText.setText(...);
        // userStatusText.setText(...);
        // profileImage.setImageResource(...);
    }
}
