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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class OtpActivity extends AppCompatActivity {

    private static final String TAG = "OtpActivity";

    private TextInputEditText etOtp;
    private Button btnSendOtp;
    private FirebaseAuth mAuth;
    private String verificationId;
    private String email;
    private String phone;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_otp);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.otp_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        etOtp = findViewById(R.id.otpEditText);
        btnSendOtp = findViewById(R.id.sendOtpButton);

        // Lấy dữ liệu từ Intent
        verificationId = getIntent().getStringExtra("verificationId");
        email = getIntent().getStringExtra("email");
        phone = getIntent().getStringExtra("phone");
        if (TextUtils.isEmpty(verificationId) || TextUtils.isEmpty(email) || TextUtils.isEmpty(phone)) {
            Log.e(TAG, "Dữ liệu không đầy đủ: verificationId=" + verificationId + ", email=" + email + ", phone=" + phone);
            Toast.makeText(this, "Lỗi: Dữ liệu không hợp lệ.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        btnSendOtp.setOnClickListener(v -> verifyOtp());
    }

    private void verifyOtp() {
        String otp = etOtp.getText().toString().trim();

        if (TextUtils.isEmpty(otp)) {
            etOtp.setError("Vui lòng nhập mã OTP.");
            return;
        }

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otp);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Xác minh số điện thoại thành công.");
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            saveUserProfileToFirestore(user.getUid(), email, phone);
                            Intent intent = new Intent(this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Log.e(TAG, "Xác minh OTP thất bại: " + task.getException());
                        Toast.makeText(this, "Mã OTP không đúng. Vui lòng kiểm tra lại.", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void saveUserProfileToFirestore(String uid, String email, String phone) {
        Log.d(TAG, "Lưu hồ sơ người dùng vào Firestore: UID = " + uid);

        Map<String, Object> user = new HashMap<>();
        user.put("ID", uid);
        user.put("Email", email);
        user.put("FullName", "");
        user.put("Phone", phone); // Lưu số điện thoại thực tế
        user.put("CCCD", "");
        user.put("QueQuan", "");
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
}