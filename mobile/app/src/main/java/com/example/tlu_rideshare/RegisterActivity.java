package com.example.tlu_rideshare;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
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

        etDob.setOnClickListener(v -> showDatePickerDialog(etDob));

        btnRegister.setOnClickListener(v -> registerUser());
    }

    private void showDatePickerDialog(final EditText editText) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String date = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear);
                    editText.setText(date);
                }, year, month, day);
        dialog.show();
    }

    private void registerUser() {
        String fullName = etFullName.getText().toString().trim();
        String dob = etDob.getText().toString().trim();
        String phoneNumber = etPhoneNumber.getText().toString().trim();
        String hometown = etHometown.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(fullName)) {
            etFullName.setError("Vui lòng nhập họ và tên.");
            return;
        }
        if (TextUtils.isEmpty(dob)) {
            etDob.setError("Vui lòng chọn ngày sinh.");
            return;
        }
        if (TextUtils.isEmpty(phoneNumber)) {
            etPhoneNumber.setError("Vui lòng nhập số điện thoại.");
            return;
        }
        if (TextUtils.isEmpty(hometown)) {
            etHometown.setError("Vui lòng nhập quê quán.");
            return;
        }
        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Vui lòng nhập email.");
            return;
        }
        if (TextUtils.isEmpty(password) || password.length() < 6) {
            etPassword.setError("Mật khẩu phải có ít nhất 6 ký tự.");
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Tạo tài khoản thành công.");
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            saveUserDataToFirestore(user.getUid(), fullName, dob, phoneNumber, hometown, email);
                            user.sendEmailVerification()
                                    .addOnCompleteListener(verificationTask -> {
                                        if (verificationTask.isSuccessful()) {
                                            Log.d(TAG, "Gửi email xác thực thành công.");
                                            Toast.makeText(RegisterActivity.this, "Đăng ký thành công! Vui lòng kiểm tra email để xác minh.", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Log.e(TAG, "Gửi email xác thực thất bại.", verificationTask.getException());
                                            Toast.makeText(RegisterActivity.this, "Đăng ký thành công nhưng gửi email xác thực thất bại.", Toast.LENGTH_LONG).show();
                                        }
                                    });
                        }
                    } else {
                        Log.e(TAG, "Tạo tài khoản thất bại.", task.getException());
                        Toast.makeText(RegisterActivity.this, "Đăng ký thất bại. Email đã tồn tại hoặc lỗi khác.", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void saveUserDataToFirestore(String uid, String fullName, String dob, String phoneNumber, String hometown, String email) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("fullName", fullName);
        userData.put("dob", dob);
        userData.put("phoneNumber", phoneNumber);
        userData.put("hometown", hometown);
        userData.put("email", email);
        userData.put("EmailVerified", false); // Mặc định chưa xác minh

        db.collection("users").document(uid)
                .set(userData)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Lưu thông tin người dùng vào Firestore thành công.");
                    } else {
                        Log.e(TAG, "Lưu thông tin người dùng vào Firestore thất bại.", task.getException());
                    }
                });
    }
}