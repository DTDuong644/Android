package com.example.tlu_rideshare;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tlu_rideshare.model.Trip;
import com.google.firebase.firestore.FirebaseFirestore;

public class driver_detail_trip extends AppCompatActivity {

    private TextView tvTenChuyen, tvThoiGian, tvFrom, tvTo, tvSoKhach, tvSoGhe, tvLoaiXe, tvBienSo, tvGiaVe;
    private ImageButton imageButtonBack;
    private Button btnHuyChuyen;

    private FirebaseFirestore db;
    private Trip trip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_detail_trip);

        // Khởi tạo Firestore
        db = FirebaseFirestore.getInstance();

        // Ánh xạ các view
        tvTenChuyen = findViewById(R.id.tvTenChuyen);
        tvThoiGian = findViewById(R.id.tvThoiGian);
        tvFrom = findViewById(R.id.tvFrom);
        tvTo = findViewById(R.id.tvTo);
        tvSoKhach = findViewById(R.id.tvSoKhach);
        tvSoGhe = findViewById(R.id.tvSoGhe);
        tvLoaiXe = findViewById(R.id.tvLoaiXe);
        tvBienSo = findViewById(R.id.tvBienSo);
        tvGiaVe = findViewById(R.id.tvGiaVe);
        imageButtonBack = findViewById(R.id.imageButton);
        btnHuyChuyen = findViewById(R.id.btnHuyChuyen);

        // Nhận dữ liệu chuyến đi
        trip = (Trip) getIntent().getSerializableExtra("trip");

        if (trip != null) {
            int seatsLeft = trip.getSeatsAvailable() - trip.getSeatsBooked();

            tvTenChuyen.setText("Chuyến #" + trip.getTripID());
            tvThoiGian.setText("Thời gian: " + trip.getTime() + " ngày " + trip.getDate());
            tvFrom.setText("Điểm đón: " + trip.getFromLocation());
            tvTo.setText("Điểm trả: " + trip.getToLocation());
            tvSoKhach.setText("Số khách: " + trip.getSeatsBooked());
            tvSoGhe.setText("Số ghế trống: " + seatsLeft);
            tvLoaiXe.setText("Loại xe: " + trip.getVihicleType());
            tvBienSo.setText("Biển số: " + trip.getLicensePlate());
            tvGiaVe.setText("Giá vé: " + trip.getPrice() + " VNĐ");
        }

        // Quay lại màn hình trước
        imageButtonBack.setOnClickListener(v -> finish());

        // Xử lý nút Hủy chuyến
        btnHuyChuyen.setOnClickListener(v -> {
            new android.app.AlertDialog.Builder(driver_detail_trip.this)
                    .setTitle("Xác nhận hủy chuyến đi")
                    .setMessage("Bạn chắc chắn muốn hủy chuyến đi?")
                    .setPositiveButton("Hủy chuyến đi", (dialog, which) -> {
                        if (trip != null && trip.getTripID() != null) {
                            db.collection("trips").document(trip.getTripID())
                                    .delete()
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(driver_detail_trip.this, "Đã hủy chuyến đi!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(driver_detail_trip.this, driver_trip.class);
                                        startActivity(intent);
                                        finish();
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(driver_detail_trip.this, "Lỗi khi hủy chuyến: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        } else {
                            Toast.makeText(driver_detail_trip.this, "Không tìm thấy ID chuyến đi", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Quay lại", null)
                    .show();
        });

    }
}
