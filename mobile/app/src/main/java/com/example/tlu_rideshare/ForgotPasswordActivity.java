package com.example.tlu_rideshare;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private static final String TAG = "ForgotPasswordActivity";

    private TextInputEditText etEmail;
    private Button btnSendOtp;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.forgot_password_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();

        etEmail = findViewById(R.id.emailEditText);
        btnSendOtp = findViewById(R.id.sendOtpButton);

        btnSendOtp.setOnClickListener(v -> sendPasswordResetEmail());
    }

    private void sendPasswordResetEmail() {
        String email = etEmail.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Vui lòng nhập email.");
            return;
        }

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Email khôi phục mật khẩu đã được gửi.");
                        Toast.makeText(ForgotPasswordActivity.this, "Đã gửi email khôi phục. Vui lòng kiểm tra email (bao gồm thư mục spam) và nhấp liên kết để đặt lại mật khẩu.", Toast.LENGTH_LONG).show();
                        finish(); // Kết thúc activity sau khi gửi email
                    } else {
                        Log.e(TAG, "Gửi email khôi phục thất bại.", task.getException());
                        String errorMessage = "Gửi email khôi phục thất bại. Vui lòng kiểm tra email.";
                        if (task.getException() != null) {
                            errorMessage += " Lỗi: " + task.getException().getMessage();
                        }
                        Toast.makeText(ForgotPasswordActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                    }
                });
    }
}