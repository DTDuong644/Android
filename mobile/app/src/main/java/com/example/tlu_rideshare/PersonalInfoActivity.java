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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class PersonalInfoActivity extends AppCompatActivity {

    private static final String TAG = "PersonalInfoActivity";

    private EditText etFullName, etDob, etPhoneNumber, etHometown;
    private Button btnSubmit;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_personal_info);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.personal_info_layout), (v, insets) -> {
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
        btnSubmit = findViewById(R.id.submitButton);

        etDob.setOnClickListener(v -> showDatePickerDialog(etDob));

        btnSubmit.setOnClickListener(v -> savePersonalInfo());
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

    private void savePersonalInfo() {
        String fullName = etFullName.getText().toString().trim();
        String dob = etDob.getText().toString().trim();
        String phoneNumber = etPhoneNumber.getText().toString().trim();
        String hometown = etHometown.getText().toString().trim();

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

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            Map<String, Object> userData = new HashMap<>();
            userData.put("fullName", fullName);
            userData.put("dob", dob);
            userData.put("phoneNumber", phoneNumber);
            userData.put("hometown", hometown);
            // Lưu các trường cũ từ đăng ký (nếu có, ví dụ: email)
            userData.put("email", user.getEmail());

            db.collection("users").document(uid)
                    .set(userData)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Lưu thông tin cá nhân thành công.");
                            Toast.makeText(PersonalInfoActivity.this, "Lưu thông tin thành công!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(PersonalInfoActivity.this, ProfileActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.e(TAG, "Lưu thông tin thất bại.", task.getException());
                            Toast.makeText(PersonalInfoActivity.this, "Lưu thông tin thất bại. Vui lòng thử lại.", Toast.LENGTH_LONG).show();
                        }
                    });
        } else {
            Log.e(TAG, "Không tìm thấy người dùng hiện tại.");
            Toast.makeText(this, "Vui lòng đăng nhập lại.", Toast.LENGTH_LONG).show();
        }
    }
}