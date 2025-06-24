package com.example.tlu_rideshare.passenger;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tlu_rideshare.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {

    private EditText editTextFullName, editTextBirthday, editTextEmail, editTextHometown, editTextPhoneNumber;
    private FirebaseFirestore db;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_layout);

        // Khởi tạo Firestore và userID
        db = FirebaseFirestore.getInstance();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Khởi tạo các view
        ImageView imgBackEditProfile = findViewById(R.id.imgBackAccountStatus);
        editTextFullName = findViewById(R.id.editTextText);
        editTextBirthday = findViewById(R.id.editTextText5);
        editTextEmail = findViewById(R.id.editTextText2);
        editTextHometown = findViewById(R.id.editTextText3);
        editTextPhoneNumber = findViewById(R.id.editTextText4);
        Button btnSave = findViewById(R.id.btnSave);

        // Load dữ liệu người dùng từ Firestore
        db.collection("users").document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        editTextFullName.setText(documentSnapshot.getString("fullName"));
                        editTextBirthday.setText(documentSnapshot.getString("dob"));
                        editTextEmail.setText(documentSnapshot.getString("email"));
                        editTextHometown.setText(documentSnapshot.getString("hometown"));
                        editTextPhoneNumber.setText(documentSnapshot.getString("phoneNumber"));
                    }
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Không thể tải dữ liệu người dùng", Toast.LENGTH_SHORT).show()
                );

        // Sự kiện quay lại
        imgBackEditProfile.setOnClickListener(v -> finish());

        // Sự kiện lưu
        btnSave.setOnClickListener(v -> {
            String updatedFullName = editTextFullName.getText().toString().trim();
            String updatedDob = editTextBirthday.getText().toString().trim();
            String updatedEmail = editTextEmail.getText().toString().trim();
            String updatedHometown = editTextHometown.getText().toString().trim();
            String updatedPhoneNumber = editTextPhoneNumber.getText().toString().trim();

            Map<String, Object> updates = new HashMap<>();
            updates.put("fullName", updatedFullName);
            updates.put("dob", updatedDob);
            updates.put("email", updatedEmail);
            updates.put("hometown", updatedHometown);
            updates.put("phoneNumber", updatedPhoneNumber);

            db.collection("users").document(userId)
                    .update(updates)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Cập nhật hồ sơ thành công", Toast.LENGTH_SHORT).show();
                        finish(); // Đóng lại sau khi lưu
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Lỗi cập nhật hồ sơ", Toast.LENGTH_SHORT).show();
                    });
        });
    }
}
