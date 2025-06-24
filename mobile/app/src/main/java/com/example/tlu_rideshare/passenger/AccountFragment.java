package com.example.tlu_rideshare.passenger;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tlu_rideshare.R;
import com.example.tlu_rideshare.model.User;
import com.google.firebase.firestore.FirebaseFirestore;

public class AccountFragment extends Fragment {

    private TextView txtFullName;
    private Button btnHistoryList, btnRate, btnRepairResume;
    private User customer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.account_fragment_passenger_layout, container, false);

        txtFullName = view.findViewById(R.id.txtFullName);
        btnHistoryList = view.findViewById(R.id.btnHistoryList);
        btnRate = view.findViewById(R.id.btnYourRate);
        btnRepairResume = view.findViewById(R.id.btnRepairResume);

        SharedPreferences prefs = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String customerId = prefs.getString("customerId", "unknown_id");
        Log.d("AccountFragment", "customerId = " + customerId);

        // Load dữ liệu từ Firestore
        FirebaseFirestore.getInstance()
                .collection("users")
                .document(customerId)
                .get()
                .addOnSuccessListener(document -> {
                    if (document.exists()) {
                        customer = new User(
                                customerId,
                                document.getString("fullName"),
                                document.getString("email"),
                                document.getString("phoneNumber"),
                                document.getString("hometown"),
                                document.getString("dob"),
                                Boolean.TRUE.equals(document.getBoolean("EmailVerified"))
                        );
                        // ✅ Gọi sau khi đã có dữ liệu
                        txtFullName.setText(customer.getFullName());
                    } else {
                        Toast.makeText(getContext(), "Không tìm thấy người dùng", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Lỗi tải người dùng", Toast.LENGTH_SHORT).show();
                });

        // Các sự kiện
        btnHistoryList.setOnClickListener(v -> startActivity(new Intent(getActivity(), HistoryListActivity.class)));

        btnRate.setOnClickListener(v -> {
            if (customer == null) {
                Toast.makeText(getContext(), "Chưa tải xong thông tin người dùng", Toast.LENGTH_SHORT).show();
                return;
            }
            FirebaseFirestore.getInstance()
                    .collection("bookings")
                    .whereEqualTo("userID", customer.getId())
                    .whereEqualTo("rated", true)
                    .limit(1)
                    .get()
                    .addOnSuccessListener(snapshot -> {
                        if (!snapshot.isEmpty()) {
                            startActivity(new Intent(getActivity(), YourRatingActivity.class));
                        } else {
                            Toast.makeText(getContext(), "Bạn chưa có chuyến đi nào đã được đánh giá", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getContext(), "Lỗi kết nối đến hệ thống", Toast.LENGTH_SHORT).show();
                    });
        });

        btnRepairResume.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), EditProfile.class);
            startActivity(intent);
        });


        return view;
    }
}