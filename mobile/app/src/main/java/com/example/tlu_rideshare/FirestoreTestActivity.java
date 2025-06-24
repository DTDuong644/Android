package com.example.tlu_rideshare;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FirestoreTestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> test = new HashMap<>();
        test.put("hello", "world");
        test.put("time", System.currentTimeMillis());

        db.collection("test").add(test)
                .addOnSuccessListener(documentReference -> {
                    Log.d("FIRETEST", "✅ Ghi thành công: " + documentReference.getId());
                })
                .addOnFailureListener(e -> {
                    Log.e("FIRETEST", "❌ Ghi thất bại: " + e.getMessage(), e);
                });
    }
}
