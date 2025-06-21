package com.example.tlurideshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SendOTPActivity extends AppCompatActivity {

    private EditText editTextSendOTP, editTextConfirmOTP;
    private Button btnGetOTP, btnConfirmOTP;
    private ImageView imgBackSendOTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_otp);

        // Ánh xạ view
        editTextSendOTP = findViewById(R.id.editTextSendOTP);
        editTextConfirmOTP = findViewById(R.id.editTextConfirmOTP);
        btnGetOTP = findViewById(R.id.btnGetOTP);
        btnConfirmOTP = findViewById(R.id.btnConfirmOTP);
        imgBackSendOTP = findViewById(R.id.imgBackSendOTP);

        // Nút quay lại
        imgBackSendOTP.setOnClickListener(v -> finish());

        // Nút Gửi OTP
        btnGetOTP.setOnClickListener(v -> {
            String input = editTextSendOTP.getText().toString().trim();
            if (input.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập email hoặc số điện thoại", Toast.LENGTH_SHORT).show();
            } else {
                // TODO: Gửi OTP đến email/số điện thoại
                Toast.makeText(this, "Mã OTP đã được gửi", Toast.LENGTH_SHORT).show();
            }
        });

        // Nút Xác nhận OTP
        btnConfirmOTP.setOnClickListener(v -> {
            String otp = editTextConfirmOTP.getText().toString().trim();
            if (otp.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập mã OTP", Toast.LENGTH_SHORT).show();
            } else {
                // TODO: Kiểm tra mã OTP (giả lập)
                if (otp.equals("123456")) {
                    Toast.makeText(this, "Xác thực thành công!", Toast.LENGTH_SHORT).show();
                    // Chuyển về ProfileActivity hoặc màn hình tiếp theo
                    Intent intent = new Intent(SendOTPActivity.this, PersonalInfoActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "Mã OTP không đúng", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
