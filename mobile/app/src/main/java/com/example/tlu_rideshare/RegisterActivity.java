package com.example.tlu_rideshare;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    private EditText etFullName, etDob, etPhoneNumber, etHometown, etEmail, etPassword;
    private Button btnRegister;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.register_layout), (v, insets) -> {
            v.setPadding(
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).left,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).top,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).right,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom
            );
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        etFullName = findViewById(R.id.fullNameEditText);
        etDob = findViewById(R.id.dobEditText);
        etPhoneNumber = findViewById(R.id.phoneNumberEditText);
        etHometown = findViewById(R.id.hometownEditText);
        etEmail = findViewById(R.id.emailEditText);
        etPassword = findViewById(R.id.passwordEditText);
        btnRegister = findViewById(R.id.registerButton);

        etDob.setOnClickListener(v -> showDatePickerDialog());

        btnRegister.setOnClickListener(v -> registerUser());
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            String date = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year);
            etDob.setText(date);
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    private void registerUser() {
        String fullName = etFullName.getText().toString().trim();
        String dob = etDob.getText().toString().trim();
        String phoneNumber = etPhoneNumber.getText().toString().trim();
        String hometown = etHometown.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(fullName)) { etFullName.setError("Nhập tên"); return; }
        if (TextUtils.isEmpty(dob)) { etDob.setError("Chọn ngày sinh"); return; }
        if (TextUtils.isEmpty(phoneNumber)) { etPhoneNumber.setError("Nhập SĐT"); return; }
        if (TextUtils.isEmpty(hometown)) { etHometown.setError("Nhập quê quán"); return; }
        if (TextUtils.isEmpty(email)) { etEmail.setError("Nhập email"); return; }
        if (TextUtils.isEmpty(password) || password.length() < 6) {
            etPassword.setError("Mật khẩu >= 6 ký tự"); return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            saveUserDataToFirestore(user.getUid(), fullName, dob, phoneNumber, hometown, email);
                            // ✅ Lưu customerId
                            SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                            prefs.edit().putString("customerId", user.getUid()).apply();

                            user.sendEmailVerification().addOnCompleteListener(vTask -> {
                                Toast.makeText(this, "Vui lòng kiểm tra email để xác minh!", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                finish();
                            });
                        }
                    } else {
                        Toast.makeText(this, "Lỗi đăng ký: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void saveUserDataToFirestore(String uid, String fullName, String dob, String phoneNumber, String hometown, String email) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("id", uid);
        userData.put("fullName", fullName);
        userData.put("dob", dob);
        userData.put("phoneNumber", phoneNumber);
        userData.put("hometown", hometown);
        userData.put("email", email);
        userData.put("EmailVerified", false);

        db.collection("users").document(uid).set(userData)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Lưu dữ liệu thành công"))
                .addOnFailureListener(e -> Log.e(TAG, "Lỗi lưu Firestore", e));
    }
}
