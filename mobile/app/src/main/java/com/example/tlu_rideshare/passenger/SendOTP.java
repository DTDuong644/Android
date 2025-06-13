package com.example.tlu_rideshare.passenger;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tlu_rideshare.R;

import java.util.Random;

public class SendOTP extends AppCompatActivity {
    private static final String TAG = "SendOTP";
    private ImageView imgBack;
    private EditText editTextSendOTP, editTextConfirmOTP;
    private Button btnGetOTP, btnConfirmOTP;
    private String generatedOTP;
    private String registeredPhoneNumber;
    private String registeredEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.send_otp);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imgBack = findViewById(R.id.imgBackSendOTP);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Khởi tạo các thành phần giao diện
        imgBack = findViewById(R.id.imgBackSendOTP);
        editTextSendOTP = findViewById(R.id.editTextSendOTP);
        editTextConfirmOTP = findViewById(R.id.editTextConfirmOTP);
        btnGetOTP = findViewById(R.id.btnGetOTP);
        btnConfirmOTP = findViewById(R.id.btnConfirmOTP);

        // Lấy thông tin Customer từ SharedPreferences
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        registeredPhoneNumber = prefs.getString("phoneNumber", "");
        registeredEmail = prefs.getString("email", "");
        Log.d(TAG, "Registered Phone: " + registeredPhoneNumber + ", Email: " + registeredEmail);

        // Xử lý sự kiện nhấn nút Gửi OTP
        btnGetOTP.setOnClickListener(v -> {
            String input = editTextSendOTP.getText().toString().trim();
            if (input.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập số điện thoại hoặc email", Toast.LENGTH_SHORT).show();
                return;
            }

            // Kiểm tra đầu vào có khớp với Customer không
            if (!input.equals(registeredPhoneNumber) && !input.equals(registeredEmail)) {
                Toast.makeText(this, "Số điện thoại hoặc email không đúng", Toast.LENGTH_SHORT).show();
                return;
            }

            // Kiểm tra định dạng (tùy chọn)
            if (!isValidPhone(input) && !isValidEmail(input)) {
                Toast.makeText(this, "Định dạng số điện thoại hoặc email không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }
            btnConfirmOTP.setEnabled(true);
        });

        // Xử lý sự kiện nhấn nút Xác nhận OTP
        btnConfirmOTP.setOnClickListener(v -> {
            String enteredOTP = editTextConfirmOTP.getText().toString().trim();
            if (enteredOTP.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập mã OTP", Toast.LENGTH_SHORT).show();
                return;
            }

            // Xác minh OTP
            if (enteredOTP.equals(generatedOTP)) {
                // Cập nhật trạng thái xác minh
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("isAccountVerified", true);
                editor.apply();

                Toast.makeText(this, "Xác minh thành công!", Toast.LENGTH_SHORT).show();

                // Chuyển đến AccountStatusTrue
                Intent intent = new Intent(SendOTP.this, AccountStatusTrue.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Mã OTP không đúng", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isValidPhone(String phone) {
        return phone.matches("\\d{10,11}");
    }

    // Kiểm tra định dạng email (đơn giản)
    private boolean isValidEmail(String email) {
        return email.matches("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }
}