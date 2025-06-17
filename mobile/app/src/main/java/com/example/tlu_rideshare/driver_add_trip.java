package com.example.tlu_rideshare;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class driver_add_trip extends AppCompatActivity {

    EditText edt_date;
    EditText edtTime;

    EditText edtVehicleType;

    EditText edtSeats;
    EditText edtPrice;
    Button btnSave;
    EditText edtPickup;

    EditText edtDestination;
    EditText lastClickedEditText;

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
        edt_date = findViewById(R.id.edt_date);
        edtVehicleType = findViewById(R.id.edt_vehicle_type);
        edtTime = findViewById(R.id.edt_time);
        edtSeats = findViewById(R.id.edt_seats);
        edtPrice = findViewById(R.id.edt_price);
        btnSave = findViewById(R.id.btn_save);
        edtPickup = findViewById(R.id.edt_pickup);
        edtDestination = findViewById(R.id.edt_destination);


        edt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        edtVehicleType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showVehiclePicker();
            }
        });
        edtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTime();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveButtonClicked();
            }
        });
        edtPickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLocationDialog(edtPickup);
            }
        });

        edtDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLocationDialog(edtDestination);
            }
        });


    }
    private void showDatePicker(){
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Chọn ngày đi")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();

        datePicker.show(getSupportFragmentManager(), "DATE_PICKER");

        datePicker.addOnPositiveButtonClickListener(selection -> {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(selection);
            // Định dạng thành dd/MM/yyyy
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            edt_date.setText(sdf.format(calendar.getTime()));
        });
    }
    private void showVehiclePicker() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View sheetView = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_vehicle, null);
        bottomSheetDialog.setContentView(sheetView);

        LinearLayout optionCar = sheetView.findViewById(R.id.option_car);
        LinearLayout optionBike = sheetView.findViewById(R.id.option_bike);
        EditText edtVehicleType = findViewById(R.id.edt_vehicle_type); // lấy lại nếu cần

        optionCar.setOnClickListener(v -> {
            edtVehicleType.setText("Ô tô");
            bottomSheetDialog.dismiss();
        });

        optionBike.setOnClickListener(v -> {
            edtVehicleType.setText("Xe máy");
            bottomSheetDialog.dismiss();
        });

        bottomSheetDialog.show();
    }
    private void selectTime(){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                (view, selectedHour, selectedMinute) -> {
                    edtTime.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                },
                hour, minute, true);

        timePickerDialog.show();
    }
    private void onSaveButtonClicked() {
        EditText edtSeats = findViewById(R.id.edt_seats);
        EditText edtPrice = findViewById(R.id.edt_price);

        String seatsStr = edtSeats.getText().toString().trim();
        String priceStr = edtPrice.getText().toString().trim();

        // Kiểm tra số ghế
        if (seatsStr.isEmpty() || !isPositiveInteger(seatsStr)) {
            edtSeats.setError("Số ghế phải là số nguyên > 0");
            Toast.makeText(this, "Vui lòng nhập số ghế hợp lệ (> 0)", Toast.LENGTH_SHORT).show();
            edtSeats.requestFocus();
            return;
        }

        // Kiểm tra giá tiền là bội số của 1000
        if (priceStr.isEmpty() || !isMultipleOfThousand(priceStr)) {
            edtPrice.setError("Giá vé phải là bội số của 1000");
            Toast.makeText(this, "Giá vé phải là bội số của 1000", Toast.LENGTH_SHORT).show();
            edtPrice.requestFocus();
            return;
        }

        // ✅ Nếu hợp lệ, có thể tiếp tục xử lý lưu chuyến đi tại đây
        Toast.makeText(this, "Dữ liệu hợp lệ! Tiến hành lưu...", Toast.LENGTH_SHORT).show();
    }

    private boolean isPositiveInteger(String s) {
        try {
            int value = Integer.parseInt(s);
            return value > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isMultipleOfThousand(String s) {
        try {
            int value = Integer.parseInt(s);
            return value % 1000 == 0 && value > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private void showLocationDialog(EditText targetEditText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_select_location, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();

        TextView btnOther = view.findViewById(R.id.btn_other_locations);
        TextView btnTlu = view.findViewById(R.id.btn_tlu);

        btnOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                // Mở màn hình chọn tỉnh khác và truyền targetEditText để xử lý kết quả
                Intent intent = new Intent(driver_add_trip.this, SelectOtherLocation.class);
                startActivityForResult(intent, 1001); // Code kết quả riêng để phân biệt
                lastClickedEditText = targetEditText; // Gán để xử lý khi onActivityResult
            }
        });

        btnTlu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                targetEditText.setText("TLU");
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1001 && resultCode == RESULT_OK && data != null) {
            String selectedLocation = data.getStringExtra("selected_location");
            if (lastClickedEditText != null) {
                lastClickedEditText.setText(selectedLocation);
            }
        }
    }



}