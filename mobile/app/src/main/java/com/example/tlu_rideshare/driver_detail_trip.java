package com.example.tlu_rideshare;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tlu_rideshare.model.Trip;

public class driver_detail_trip extends AppCompatActivity {

    private TextView tvTenChuyen, tvThoiGian, tvFrom, tvTo, tvSoKhach, tvSoGhe, tvLoaiXe, tvBienSo, tvGiaVe;
    private ImageButton imageButtonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_detail_trip);

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

        // Nhận đối tượng Trip từ Intent
        Trip trip = (Trip) getIntent().getSerializableExtra("trip");

        if (trip != null) {
            int seatsLeft = trip.getSeatsAvailable() - trip.getSeatsBooked();

            tvTenChuyen.setText("Chuyến #" + trip.getId());
            tvThoiGian.setText("Thời gian: " + trip.getTime() + " ngày " + trip.getDate());
            tvFrom.setText("Điểm đón: " + trip.getFromLocation());
            tvTo.setText("Điểm trả: " + trip.getToLocation());
            tvSoKhach.setText("Số khách: " + trip.getSeatsBooked());
            tvSoGhe.setText("Số ghế trống: " + seatsLeft);
            tvLoaiXe.setText("Loại xe: " + trip.getCarType());
            tvBienSo.setText("Biển số: " + trip.getLicensePlate());
            tvGiaVe.setText("Giá vé: " + trip.getPrice() + " VNĐ");
        }

        // Xử lý nút quay lại
        imageButtonBack.setOnClickListener(v -> finish());
    }
}
