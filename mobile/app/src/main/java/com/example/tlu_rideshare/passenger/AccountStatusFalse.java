package com.example.tlu_rideshare.passenger;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tlu_rideshare.R;

public class AccountStatusFalse extends AppCompatActivity {

    private static final String TAG = "AccountStatusFalse";
    private Button btnVerifyNow;
    private ImageView imgBackAccountStatusFalse, imgAvtStatusF;
    private TextView tvNameAccountF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.account_status_false_layout);

        // Xử lý padding cho system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Khởi tạo các thành phần giao diện
        btnVerifyNow = findViewById(R.id.btnVerifyNow);
        imgBackAccountStatusFalse = findViewById(R.id.imgBackAccountStatusFalse);
        imgAvtStatusF = findViewById(R.id.imgAvtStatusF);
        tvNameAccountF = findViewById(R.id.tvNameAccountF);

        // Cập nhật ảnh đại diện và tên tài khoản từ SharedPreferences
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String defaultAvatarUri = "android.resource://" + getPackageName() + "/" + R.drawable.avatar;
        String avatar = prefs.getString("avatar", defaultAvatarUri);
        String fullName = prefs.getString("fullName", "Tên người dùng");

        // Cập nhật tên tài khoản
        tvNameAccountF.setText(fullName);

        // Cập nhật ảnh đại diện
        try {
            if (avatar != null && !avatar.isEmpty()) {
                imgAvtStatusF.setImageURI(Uri.parse(avatar));
            } else {
                imgAvtStatusF.setImageResource(R.drawable.avatar);
            }
        } catch (Exception e) {
            Log.w(TAG, "Lỗi khi tải ảnh đại diện: " + e.getMessage());
            imgAvtStatusF.setImageResource(R.drawable.avatar);
        }

        // Xử lý sự kiện nhấn btnVerifyNow
        btnVerifyNow.setOnClickListener(v -> {
            Log.d(TAG, "Nút Verify Now được nhấn");
            try {
                Intent intent = new Intent(AccountStatusFalse.this, SendOTP.class);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                Log.e(TAG, "Lỗi khi khởi động SendOTP: ", e);
                Toast.makeText(this, "Không thể mở trang xác minh", Toast.LENGTH_SHORT).show();
            }
        });

        // Xử lý sự kiện nhấn imgBackAccountStatusFalse
        imgBackAccountStatusFalse.setOnClickListener(v -> {
            Log.d(TAG, "Nút Back được nhấn");
            finish(); // Kết thúc hoạt động, quay lại AccountFragment
        });
    }
}