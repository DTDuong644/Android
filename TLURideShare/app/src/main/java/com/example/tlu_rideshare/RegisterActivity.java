package com.example.tlu_rideshare;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tlurideshare.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    private TextInputEditText etEmail;
    private TextInputEditText etPassword;
    private TextInputEditText etConfirmPassword;
    private Button btnRegister;
    private TextView tvLoginNow;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register); // ✅ Đúng layout

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.register_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Đang đăng ký tài khoản...");
        progressDialog.setCancelable(false);

        etEmail = findViewById(R.id.emailEditText);
        etPassword = findViewById(R.id.passwordEditText);
        etConfirmPassword = findViewById(R.id.confirmPasswordEditText);
        btnRegister = findViewById(R.id.registerButton);
        tvLoginNow = findViewById(R.id.loginTextView);

        btnRegister.setOnClickListener(v -> {
            Log.d(TAG, "Click đăng ký");
            registerUser();
        });

        tvLoginNow.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, com.example.tlu_rideshare.LoginActivity.class));
            finish();
        });
    }

    private void registerUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();
        String fullName = "";
        String phoneNumber = "";
        String cccd = "";
        String queQuan = "";

        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Email không được để trống.");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Mật khẩu không được để trống.");
            return;
        }
        if (password.length() < 6) {
            etPassword.setError("Mật khẩu phải có ít nhất 6 ký tự.");
            return;
        }
        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Mật khẩu xác nhận không khớp.");
            return;
        }

        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            saveUserProfileToFirestore(user.getUid(), email, fullName, phoneNumber, cccd, queQuan);
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(this, "Đăng ký thành công nhưng không tìm thấy người dùng.", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        progressDialog.dismiss();
                        String errorMessage = "Đăng ký thất bại.";
                        Exception e = task.getException();
                        if (e instanceof FirebaseAuthWeakPasswordException) {
                            errorMessage = "Mật khẩu quá yếu.";
                        } else if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            errorMessage = "Email không hợp lệ.";
                        } else if (e instanceof FirebaseAuthUserCollisionException) {
                            errorMessage = "Email đã được sử dụng.";
                        }
                        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void saveUserProfileToFirestore(String uid, String email, String fullName, String phone, String cccd, String queQuan) {
        Map<String, Object> user = new HashMap<>();
        user.put("ID", uid);
        user.put("Email", email);
        user.put("FullName", fullName);
        user.put("Phone", phone);
        user.put("CCCD", cccd);
        user.put("QueQuan", queQuan);

        db.collection("User").document(uid).set(user)
                .addOnCompleteListener(task -> {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(this, com.example.tlu_rideshare.LoginActivity.class));
                        finish();
                    } else {
                        String msg = "Lỗi khi lưu hồ sơ: " + task.getException().getMessage();
                        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
                    }
                });
    }
}
