package com.example.tlu_rideshare;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
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


}