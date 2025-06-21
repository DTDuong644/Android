package com.example.tlu_rideshare;

import android.os.Bundle;
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
import java.util.List;

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
        userAdapter = new UserAdapter(this, userList);
        recyclerCustomer.setLayoutManager(new LinearLayoutManager(this));
        recyclerCustomer.setAdapter(userAdapter);

        db = FirebaseFirestore.getInstance();

        loadCustomerList();
    }

    private void loadCustomerList() {
//        CollectionReference usersRef = db.collection("users");
//
//        usersRef.whereEqualTo("role", "customer").get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        userList.clear();
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot doc : task.getResult()) {
//                                User user = doc.toObject(User.class);
//                                userList.add(user);
//                            }
//                            userAdapter.notifyDataSetChanged();
//                        } else {
//                            Toast.makeText(driver_customer_list.this,
//                                    "Lỗi tải dữ liệu: " + task.getException().getMessage(),
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
        userList.clear();

        userList.add(new User(
                "uid001",
                "Nguyễn Văn A",
                "a@gmail.com",
                "0123456789",
                "Nhà A2, Đại học Thủy Lợi",
                "Nhà xxx, đường yy, xã z, huyện k, tỉnh hhh",
                "customer"
        ));

        userList.add(new User(
                "uid002",
                "Nguyễn Văn B",
                "b@gmail.com",
                "0987654321",
                "Nhà A2, Đại học Thủy Lợi",
                "Số 5, đường abc, tỉnh xyz",
                "customer"
        ));

        userList.add(new User(
                "uid003",
                "Nguyễn Văn C",
                "c@gmail.com",
                "0911222333",
                "Nhà A2, Đại học Thủy Lợi",
                "Thị trấn abc, huyện def",
                "customer"
        ));

        userAdapter.notifyDataSetChanged();
    }
}
