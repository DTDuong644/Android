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
    private Button btnHuyChuyen, btnKhachHang, btnSuaThongTin, btnHoanThanh;

    private FirebaseFirestore db;
    private Trip trip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_detail_trip);

        db = FirebaseFirestore.getInstance();

        // Ánh xạ view
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
        btnSuaThongTin = findViewById(R.id.btnSuaThongTin);
        btnHoanThanh = findViewById(R.id.btnHoanThanh);
        btnKhachHang = findViewById(R.id.btnKhachHang);

        // ✅ Lấy dữ liệu bằng Parcelable
        trip = getIntent().getParcelableExtra("trip");

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
        } else {
            Toast.makeText(this, "Không tìm thấy thông tin chuyến đi", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        imageButtonBack.setOnClickListener(v -> finish());

        btnHuyChuyen.setOnClickListener(v -> {
            new android.app.AlertDialog.Builder(driver_detail_trip.this)
                    .setTitle("Xác nhận hủy chuyến đi")
                    .setMessage("Bạn chắc chắn muốn hủy chuyến đi?")
                    .setPositiveButton("Hủy chuyến đi", (dialog, which) -> {
                        db.collection("trips").document(trip.getTripID())
                                .delete()
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(driver_detail_trip.this, "Đã hủy chuyến đi!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(driver_detail_trip.this, driver_trip.class));
                                    finish();
                                })
                                .addOnFailureListener(e ->
                                        Toast.makeText(driver_detail_trip.this, "Lỗi khi hủy chuyến: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    })
                    .setNegativeButton("Quay lại", null)
                    .show();
        });

        btnSuaThongTin.setOnClickListener(v -> {
            Intent intent = new Intent(driver_detail_trip.this, driver_edit_trip.class);
            intent.putExtra("trip", trip);
            startActivity(intent);
        });

        btnHoanThanh.setOnClickListener(v -> {
            new android.app.AlertDialog.Builder(driver_detail_trip.this)
                    .setTitle("Xác nhận hoàn thành")
                    .setMessage("Bạn có chắc chắn muốn đánh dấu chuyến đi này là đã hoàn thành?\nTất cả booking liên quan cũng sẽ được cập nhật.")
                    .setPositiveButton("Xác nhận", (dialog, which) -> {
                        db.collection("trips").document(trip.getTripID())
                                .update("status", "complete")
                                .addOnSuccessListener(aVoid -> {
                                    db.collection("bookings")
                                            .whereEqualTo("tripID", trip.getTripID())
                                            .get()
                                            .addOnSuccessListener(querySnapshots -> {
                                                for (var doc : querySnapshots) {
                                                    doc.getReference().update("status", "complete");
                                                }
                                                Toast.makeText(driver_detail_trip.this, "Đã hoàn thành chuyến đi và cập nhật bookings", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(driver_detail_trip.this, driver_trip.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                                finish();
                                            })
                                            .addOnFailureListener(e ->
                                                    Toast.makeText(driver_detail_trip.this, "Lỗi cập nhật bookings: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                                })
                                .addOnFailureListener(e ->
                                        Toast.makeText(driver_detail_trip.this, "Lỗi cập nhật chuyến đi: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
        });

        btnKhachHang.setOnClickListener(v -> {
            Intent intent = new Intent(driver_detail_trip.this, driver_customer_list.class);
            intent.putExtra("tripID", trip.getTripID());
            intent.putExtra("fromLocation", trip.getFromLocation());
            intent.putExtra("toLocation", trip.getToLocation());
            startActivity(intent);
        });
    }
}
