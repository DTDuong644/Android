package com.example.tlu_rideshare.passenger;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tlu_rideshare.R;

public class AccountStatusTrue extends AppCompatActivity {

    private static final String TAG = "AccountStatusTrue";
    private Button btnConfirm;
    private ImageView imgBackAccountStatusTrue, imgAvtStatus;
    private TextView tvNameAccountT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.account_status_true_layout);

        // Xử lý padding cho system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Khởi tạo các thành phần giao diện
        btnConfirm = findViewById(R.id.btnConfirm);
        imgBackAccountStatusTrue = findViewById(R.id.imgBackAccountStatusTrue);
        imgAvtStatus = findViewById(R.id.imgAvtStatus);
        tvNameAccountT = findViewById(R.id.tvNameAccountT);

        // Cập nhật ảnh đại diện và tên tài khoản từ SharedPreferences
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String defaultAvatarUri = "android.resource://" + getPackageName() + "/" + R.drawable.avatar;
        String avatar = prefs.getString("avatar", defaultAvatarUri);
        String fullName = prefs.getString("fullName", "Tên người dùng");

        // Cập nhật tên tài khoản
        tvNameAccountT.setText(fullName);

        // Cập nhật ảnh đại diện
        try {
            if (avatar != null && !avatar.isEmpty()) {
                imgAvtStatus.setImageURI(Uri.parse(avatar));
            } else {
                imgAvtStatus.setImageResource(R.drawable.avatar);
            }
        } catch (Exception e) {
            Log.w(TAG, "Lỗi khi tải ảnh đại diện: " + e.getMessage());
            imgAvtStatus.setImageResource(R.drawable.avatar);
        }

        // Xử lý sự kiện nhấn btnConfirm
        btnConfirm.setOnClickListener(v -> {
            finish();
        });

        // Xử lý sự kiện nhấn imgBackAccountStatusTrue
        imgBackAccountStatusTrue.setOnClickListener(v -> {
            finish();
        });
    }
}