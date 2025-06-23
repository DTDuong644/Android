package com.example.tlu_rideshare;

import android.app.ProgressDialog;
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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    private TextInputEditText etEmail, etPassword, etConfirmPassword, etPhone;
    private Button btnRegister;
    private TextView tvLoginNow;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.register_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

        FirebaseFirestore.setLoggingEnabled(true);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Đang đăng ký tài khoản...");
        progressDialog.setCancelable(false);

        etEmail = findViewById(R.id.emailEditText);
        etPassword = findViewById(R.id.passwordEditText);
        etConfirmPassword = findViewById(R.id.confirmPasswordEditText);
        etPhone = findViewById(R.id.phoneEditText);
        btnRegister = findViewById(R.id.registerButton);
        tvLoginNow = findViewById(R.id.loginTextView);

        btnRegister.setOnClickListener(v -> registerUser());
        tvLoginNow.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    private void registerUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Email không được để trống.");
            etEmail.requestFocus();
            return;
        }
        if (!isValidEmail(email)) {
            etEmail.setError("Email không hợp lệ.");
            etEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Mật khẩu không được để trống.");
            etPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            etPassword.setError("Mật khẩu phải có ít nhất 6 ký tự.");
            etPassword.requestFocus();
            return;
        }
        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Mật khẩu xác nhận không khớp.");
            etConfirmPassword.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            etPhone.setError("Số điện thoại không được để trống.");
            etPhone.requestFocus();
            return;
        }
        if (!isValidPhone(phone)) {
            etPhone.setError("Số điện thoại không hợp lệ (phải là 10-11 số).");
            etPhone.requestFocus();
            return;
        }

        progressDialog.show();
        Log.d(TAG, "🚀 Bắt đầu tạo tài khoản Firebase với email: " + email + ", phone: " + phone);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Đăng ký Firebase Auth thành công.");
                        FirebaseUser user = task.getResult().getUser();
                        if (user != null) {
                            Log.d(TAG, "UID từ task.getResult(): " + user.getUid());
                            sendEmailVerification(user, phone); // Gửi email xác thực
                        }
                    } else {
                        progressDialog.dismiss();
                        Log.e(TAG, "Đăng ký thất bại.", task.getException());

                        String errorMessage = "Đăng ký thất bại.";
                        Exception e = task.getException();
                        if (e instanceof FirebaseAuthWeakPasswordException) {
                            errorMessage = "Mật khẩu quá yếu.";
                        } else if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            errorMessage = "Email không hợp lệ.";
                        } else if (e instanceof FirebaseAuthUserCollisionException) {
                            errorMessage = "Email đã tồn tại.";
                        } else if (e != null && e.getMessage() != null && e.getMessage().contains("network error")) {
                            errorMessage = "Lỗi mạng. Kiểm tra kết nối.";
                        } else if (e != null) {
                            errorMessage = "Lỗi: " + e.getMessage();
                        }

                        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void sendEmailVerification(FirebaseUser user, String phone) {
        user.sendEmailVerification()
                .addOnCompleteListener(this, task -> {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Email xác thực đã được gửi.");
                        Toast.makeText(this, "Đã gửi email xác thực. Vui lòng kiểm tra email (bao gồm thư mục spam) và nhấp liên kết để xác minh.", Toast.LENGTH_LONG).show();
                        saveUserProfileToFirestore(user.getUid(), user.getEmail(), "", phone, "", ""); // Lưu số điện thoại
                        Intent intent = new Intent(this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        Log.e(TAG, "Gửi email xác thực thất bại.", task.getException());
                        String errorMessage = "Gửi email xác thực thất bại. Vui lòng thử lại.";
                        if (task.getException() != null) {
                            errorMessage += " Lỗi: " + task.getException().getMessage();
                        }
                        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
                        user.delete().addOnCompleteListener(deleteTask -> {
                            if (deleteTask.isSuccessful()) {
                                Log.d(TAG, "Tài khoản đã bị xóa do lỗi gửi email.");
                            }
                        });
                    }
                });
    }

    private void saveUserProfileToFirestore(String uid, String email, String fullName, String phone, String cccd, String queQuan) {
        Log.d(TAG, "Lưu hồ sơ người dùng vào Firestore: UID = " + uid);

        Map<String, Object> user = new HashMap<>();
        user.put("ID", uid);
        user.put("Email", email);
        user.put("FullName", fullName);
        user.put("Phone", phone); // Lưu số điện thoại
        user.put("CCCD", cccd);
        user.put("QueQuan", queQuan);
        user.put("EmailVerified", false);

        db.collection("users").document(uid).set(user)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Lưu Firestore thành công.");
                    } else {
                        Exception e = task.getException();
                        String msg = "Lỗi khi lưu hồ sơ: " + (e != null ? e.getMessage() : "Không xác định.");
                        Log.e(TAG, msg, e);
                        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
                    }
                });
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    private boolean isValidPhone(String phone) {
        String phoneRegex = "^0[0-9]{9,10}$";
        Pattern pattern = Pattern.compile(phoneRegex);
        return pattern.matcher(phone).matches();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss(); // Đóng ProgressDialog khi activity bị hủy
        }
    }
}