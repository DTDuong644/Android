package com.example.tlu_rideshare;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tlu_rideshare.adapter.UserAdapter;
import com.example.tlu_rideshare.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class driver_customer_list extends AppCompatActivity {

    private RecyclerView recyclerCustomer;
    private UserAdapter userAdapter;
    private List<User> userList;
    private FirebaseFirestore db;
    private EditText edtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_driver_customer_list);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ view
        recyclerCustomer = findViewById(R.id.recyclerCustomer);
        edtSearch = findViewById(R.id.edtSearch);  // nếu có EditText tìm kiếm trong layout

        userList = new ArrayList<>();
        String tripID = getIntent().getStringExtra("tripID");
        String fromLocation = getIntent().getStringExtra("fromLocation");
        String toLocation = getIntent().getStringExtra("toLocation");

        userAdapter = new UserAdapter(this, userList, fromLocation, toLocation);
        recyclerCustomer.setLayoutManager(new LinearLayoutManager(this));
        recyclerCustomer.setAdapter(userAdapter);

        db = FirebaseFirestore.getInstance();

        String tripId = getIntent().getStringExtra("tripID");
        loadCustomerList(tripId);
    }

    private void loadCustomerList(String tripId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("bookings")
                .whereEqualTo("tripId", tripId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Set<String> userIdSet = new HashSet<>();

                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            String userId = doc.getString("userId");
                            if (userId != null) {
                                userIdSet.add(userId);
                            }
                        }

                        if (userIdSet.isEmpty()) {
                            Toast.makeText(driver_customer_list.this, "Chuyến đi này chưa có khách nào đặt.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        userList.clear();

                        for (String userId : userIdSet) {
                            db.collection("users").document(userId)
                                    .get()
                                    .addOnSuccessListener(documentSnapshot -> {
                                        if (documentSnapshot.exists()) {
                                            User user = documentSnapshot.toObject(User.class);
                                            userList.add(user);
                                            userAdapter.notifyDataSetChanged();
                                        }
                                    })
                                    .addOnFailureListener(e -> {
                                        Log.e("Firestore", "Lỗi khi lấy user: " + e.getMessage());
                                    });
                        }

                    } else {
                        Toast.makeText(driver_customer_list.this, "Lỗi khi lấy booking: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


}
