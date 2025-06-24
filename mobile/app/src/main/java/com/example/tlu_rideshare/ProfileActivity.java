package com.example.tlu_rideshare;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast; // <-- Thêm import này để hiển thị thông báo

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth; // <-- Thêm import này để sử dụng Firebase Auth
import com.google.firebase.auth.FirebaseUser; // <-- Thêm import này để lấy thông tin người dùng

public class ProfileActivity extends AppCompatActivity {

    private ImageView profileImage;
    private TextView userNameText;
    private TextView userStatusText;
    private Button editProfileButton;
    private TextView travelHistoryItem;
    private TextView yourReviewsItem;
    private TextView accountStatusItem;
    private Button logoutButton;

    private FirebaseAuth mAuth; // <-- Khai báo biến Firebase Auth

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

        // Khởi tạo Firebase Auth
        mAuth = FirebaseAuth.getInstance();

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

        // Chuyển sang AverageRatingActivity (Lịch sử chuyến đi)
        travelHistoryItem.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, AverageRatingActivity.class);
            startActivity(intent);
        });

        // Chuyển sang YourReviewsActivity (Đánh giá của bạn)
        yourReviewsItem.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, YourReviewsActivity.class);
            startActivity(intent);
        });

        // Chuyển sang AccountStatusActivity (Trạng thái tài khoản)
        accountStatusItem.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, PersonalInfoActivity.class);
            startActivity(intent);
        });

        // Xử lý nút đăng xuất
        logoutButton.setOnClickListener(v -> {
            // Đăng xuất khỏi Firebase
            mAuth.signOut(); // <-- Dòng quan trọng để đăng xuất Firebase

            // (Tùy chọn) Hiển thị thông báo đã đăng xuất
            Toast.makeText(ProfileActivity.this, "Đã đăng xuất thành công.", Toast.LENGTH_SHORT).show();

            // Chuyển sang LoginActivity và xóa tất cả các Activity trước đó khỏi Back Stack
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class); // <-- Sửa lại tên Activity đích
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish(); // Đóng ProfileActivity hiện tại
        });

        // TODO: Gán dữ liệu người dùng nếu có (tên, ảnh, trạng thái)
        // Đây là nơi bạn sẽ lấy dữ liệu người dùng từ Firebase (ví dụ: Firestore hoặc Realtime Database)
        // và hiển thị lên các TextView/ImageView
        loadUserProfileData();
    }

    private void loadUserProfileData() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // Lấy email của người dùng (ví dụ)
            userNameText.setText(user.getEmail());

            // Bạn cần thêm logic để tải ảnh profile và trạng thái người dùng
            // từ cơ sở dữ liệu (Firestore/Realtime Database) nếu bạn có lưu trữ.
            // Ví dụ:
            // FirebaseFirestore db = FirebaseFirestore.getInstance();
            // db.collection("users").document(user.getUid())
            //     .get()
            //     .addOnSuccessListener(documentSnapshot -> {
            //         if (documentSnapshot.exists()) {
            //             String name = documentSnapshot.getString("name");
            //             String status = documentSnapshot.getString("status");
            //             String imageUrl = documentSnapshot.getString("profileImageUrl");
            //
            //             if (name != null) userNameText.setText(name);
            //             if (status != null) userStatusText.setText(status);
            //
            //             // Sử dụng thư viện như Glide hoặc Picasso để tải ảnh
            //             // if (imageUrl != null && !imageUrl.isEmpty()) {
            //             //     Glide.with(this).load(imageUrl).into(profileImage);
            //             // }
            //         }
            //     })
            //     .addOnFailureListener(e -> {
            //         Toast.makeText(ProfileActivity.this, "Không thể tải dữ liệu người dùng: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            //     });
        } else {
            // Trường hợp này không nên xảy ra nếu logic chuyển hướng từ LoginActivity đúng
            // Nhưng nếu xảy ra, có thể chuyển về màn hình đăng nhập
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }
}