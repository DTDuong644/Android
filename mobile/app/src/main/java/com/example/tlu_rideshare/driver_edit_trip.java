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

public class driver_edit_trip extends AppCompatActivity {

    EditText edtPickup, edtDestination, edtDate, edtTime;
    EditText edtVehicleType, edtPlate, edtSeats, edtPrice;
    Button btnSave;
    private Trip trip;
    private FirebaseFirestore db;
    EditText lastClickedEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_edit_trip);

        db = FirebaseFirestore.getInstance();

        // Ánh xạ
        edtPickup = findViewById(R.id.edt_pickup);
        edtDestination = findViewById(R.id.edt_destination);
        edtDate = findViewById(R.id.edt_date);
        edtTime = findViewById(R.id.edt_time);
        edtVehicleType = findViewById(R.id.edt_vehicle_type);
        edtPlate = findViewById(R.id.edt_plate);
        edtSeats = findViewById(R.id.edt_seats);
        edtPrice = findViewById(R.id.edt_price);
        btnSave = findViewById(R.id.btn_save);

        trip = (Trip) getIntent().getSerializableExtra("trip");

        if (trip != null) {
            // Hiển thị dữ liệu cũ
            edtPickup.setText(trip.getFromLocation());
            edtDestination.setText(trip.getToLocation());
            edtDate.setText(trip.getDate());
            edtTime.setText(trip.getTime());
            edtVehicleType.setText(trip.getVihicleType());
            edtPlate.setText(trip.getLicensePlate());
            edtSeats.setText(String.valueOf(trip.getSeatsAvailable()));
            edtPrice.setText(String.valueOf(trip.getPrice()));
        }
        edtDate.setOnClickListener(v -> showDatePicker());
        edtTime.setOnClickListener(v -> showTimePicker());
        edtVehicleType.setOnClickListener(v -> showVehiclePicker());
        edtPickup.setOnClickListener(v -> showLocationDialog(edtPickup));
        edtDestination.setOnClickListener(v -> showLocationDialog(edtDestination));

        btnSave.setOnClickListener(v -> {
            new AlertDialog.Builder(driver_edit_trip.this)
                    .setTitle("Xác nhận cập nhật")
                    .setMessage("Bạn có chắc chắn muốn lưu các thay đổi?")
                    .setPositiveButton("Lưu", (dialog, which) -> {
                        // Lấy dữ liệu từ form
                        String from = edtPickup.getText().toString().trim();
                        String to = edtDestination.getText().toString().trim();
                        String date = edtDate.getText().toString().trim();
                        String time = edtTime.getText().toString().trim();
                        String type = edtVehicleType.getText().toString().trim();
                        String plate = edtPlate.getText().toString().trim();
                        int seats = Integer.parseInt(edtSeats.getText().toString().trim());
                        int price = Integer.parseInt(edtPrice.getText().toString().trim());

                        // Cập nhật lên Firestore
                        db.collection("trips").document(trip.getTripID())
                                .update(
                                        "fromLocation", from,
                                        "toLocation", to,
                                        "date", date,
                                        "time", time,
                                        "vihicleType", type,
                                        "licensePlate", plate,
                                        "seatsAvailable", seats,
                                        "price", price
                                )
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(driver_edit_trip.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();

                                    // Quay về danh sách chuyến đi
                                    Intent intent = new Intent(driver_edit_trip.this, driver_trip.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                })
                                .addOnFailureListener(e ->
                                        Toast.makeText(driver_edit_trip.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                                );
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
            });

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
            edtDate.setText(sdf.format(calendar.getTime()));
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
            Intent intent = new Intent(driver_edit_trip.this, SelectOtherLocation.class);
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
}
