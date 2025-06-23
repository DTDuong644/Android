package com.example.tlu_rideshare;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private TextInputEditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvForgotPassword, tvRegister; // Thêm tvRegister
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        etEmail = findViewById(R.id.emailEditText);
        etPassword = findViewById(R.id.passwordEditText);
        btnLogin = findViewById(R.id.loginButton);
        tvForgotPassword = findViewById(R.id.forgotPasswordTextView); // Đã có
        tvRegister = findViewById(R.id.registerTextView); // Thêm khai báo cho "Đăng ký ngay"

        btnLogin.setOnClickListener(v -> loginUser());
        tvForgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });
        tvRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void loginUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Email không được để trống.");
            etEmail.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Mật khẩu không được để trống.");
            etPassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Đăng nhập thành công.");
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            // Kiểm tra trạng thái email verified
                            if (user.isEmailVerified()) {
                                updateEmailVerifiedInFirestore(user.getUid(), true);
                                Intent intent = new Intent(this, ProfileActivity.class); // Chuyển sang ProfileActivity sau khi xác minh
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(this, "Vui lòng xác minh email trước khi đăng nhập.", Toast.LENGTH_LONG).show();
                                user.sendEmailVerification(); // Gửi lại email xác thực nếu chưa xác minh
                            }
                        }
                    } else {
                        Log.e(TAG, "Đăng nhập thất bại.", task.getException());
                        Toast.makeText(this, "Đăng nhập thất bại. Kiểm tra email hoặc mật khẩu.", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void updateEmailVerifiedInFirestore(String uid, boolean isVerified) {
        Map<String, Object> updates = new HashMap<>();
        updates.put("EmailVerified", isVerified);

        db.collection("users").document(uid)
                .update(updates)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Cập nhật EmailVerified trong Firestore thành công.");
                    } else {
                        Log.e(TAG, "Cập nhật EmailVerified thất bại.", task.getException());
                    }
                });
    }
}