package com.example.tlu_rideshare;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tlu_rideshare.model.Trip;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class driver_add_trip extends AppCompatActivity {

    EditText edt_date, edtTime, edtVehicleType, edtSeats, edtPrice, edtPickup, edtDestination, edtPlate;
    EditText lastClickedEditText;
    Button btnSave;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_driver_add_trip);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Khởi tạo Firebase Firestore
        db = FirebaseFirestore.getInstance();

        // Ánh xạ các thành phần giao diện
        edt_date = findViewById(R.id.edt_date);
        edtTime = findViewById(R.id.edt_time);
        edtVehicleType = findViewById(R.id.edt_vehicle_type);
        edtSeats = findViewById(R.id.edt_seats);
        edtPrice = findViewById(R.id.edt_price);
        edtPickup = findViewById(R.id.edt_pickup);
        edtDestination = findViewById(R.id.edt_destination);
        edtPlate = findViewById(R.id.edt_plate);
        btnSave = findViewById(R.id.btn_save);

        // Bắt sự kiện
        edt_date.setOnClickListener(v -> showDatePicker());
        edtTime.setOnClickListener(v -> showTimePicker());
        edtVehicleType.setOnClickListener(v -> showVehiclePicker());
        edtPickup.setOnClickListener(v -> showLocationDialog(edtPickup));
        edtDestination.setOnClickListener(v -> showLocationDialog(edtDestination));
        btnSave.setOnClickListener(v -> onSaveButtonClicked());
    }

    private void showDatePicker() {
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Chọn ngày đi")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();

        datePicker.show(getSupportFragmentManager(), "DATE_PICKER");

        datePicker.addOnPositiveButtonClickListener(selection -> {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(selection);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            edt_date.setText(sdf.format(calendar.getTime()));
        });
    }

    private void showTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog dialog = new TimePickerDialog(this,
                (view, hourOfDay, minute1) -> edtTime.setText(String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute1)),
                hour, minute, true);
        dialog.show();
    }

    private void showVehiclePicker() {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_vehicle, null);
        dialog.setContentView(view);

        LinearLayout optionCar = view.findViewById(R.id.option_car);
        LinearLayout optionBike = view.findViewById(R.id.option_bike);

        optionCar.setOnClickListener(v -> {
            edtVehicleType.setText("Ô tô");
            dialog.dismiss();
        });

        optionBike.setOnClickListener(v -> {
            edtVehicleType.setText("Xe máy");
            dialog.dismiss();
        });

        dialog.show();
    }

    private void showLocationDialog(EditText targetEditText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_select_location, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();

        TextView btnTlu = view.findViewById(R.id.btn_tlu);
        TextView btnOther = view.findViewById(R.id.btn_other_locations);

        btnTlu.setOnClickListener(v -> {
            targetEditText.setText("TLU");
            dialog.dismiss();
        });

        btnOther.setOnClickListener(v -> {
            dialog.dismiss();
            Intent intent = new Intent(driver_add_trip.this, SelectOtherLocation.class);
            startActivityForResult(intent, 1001);
            lastClickedEditText = targetEditText;
        });

        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == RESULT_OK && data != null) {
            String location = data.getStringExtra("selected_location");
            if (lastClickedEditText != null) {
                lastClickedEditText.setText(location);
            }
        }
    }

    private void onSaveButtonClicked() {
        String from = edtPickup.getText().toString().trim();
        String to = edtDestination.getText().toString().trim();
        String date = edt_date.getText().toString().trim();
        String time = edtTime.getText().toString().trim();
        String vehicleType = edtVehicleType.getText().toString().trim();
        String licensePlate = edtPlate.getText().toString().trim();
        String seatsStr = edtSeats.getText().toString().trim();
        String priceStr = edtPrice.getText().toString().trim();

        if (from.isEmpty() || to.isEmpty() || date.isEmpty() || time.isEmpty() ||
                vehicleType.isEmpty() || licensePlate.isEmpty() ||
                seatsStr.isEmpty() || priceStr.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isPositiveInteger(seatsStr)) {
            edtSeats.setError("Số ghế phải > 0");
            return;
        }

        if (!isMultipleOfThousand(priceStr)) {
            edtPrice.setError("Giá vé phải là bội số của 1000");
            return;
        }

        new AlertDialog.Builder(this)
                .setTitle("Xác nhận")
                .setMessage("Bạn có chắc chắn muốn tạo chuyến đi này?")
                .setPositiveButton("Đồng ý", (dialog, which) -> {
                    saveTripToFirestore(from, to, date, time, vehicleType, licensePlate,
                            Integer.parseInt(seatsStr), Integer.parseInt(priceStr));
                })
                .setNegativeButton("Huỷ", null)
                .show();
    }

    private void saveTripToFirestore(String from, String to, String date, String time,
                                     String vehicleType, String licensePlate, int seats, int price) {

        String tripID = db.collection("trips").document().getId();
        String driverID = "driver123"; // gán tạm driverID vì chưa có Firebase Auth
        driverID = "tnsUMBoQvzYFqPerD5dSURqcl543";
        //driverID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Trip trip = new Trip(tripID, from, to, driverID, date, time,
                seats, 0, licensePlate, vehicleType, price, "confirm");

        db.collection("trips").document(tripID).set(trip)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Tạo chuyến đi thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(driver_add_trip.this, driver_trip.class);
                    startActivity(intent);
                    finish(); // Quay lại màn hình trước
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Lỗi khi lưu: " + e.getMessage(), Toast.LENGTH_LONG).show());
    }

    private boolean isPositiveInteger(String s) {
        try {
            return Integer.parseInt(s) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isMultipleOfThousand(String s) {
        try {
            int val = Integer.parseInt(s);
            return val % 1000 == 0 && val > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
