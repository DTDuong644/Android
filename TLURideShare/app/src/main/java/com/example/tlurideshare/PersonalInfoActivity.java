package com.example.tlurideshare;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.app.DatePickerDialog;
import android.view.View;
import android.widget.EditText;
import java.util.Calendar;
import android.content.Intent;

public class PersonalInfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_personal_info);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.personal_info_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Handle Gender Dropdown
        AutoCompleteTextView genderAutoCompleteTextView = findViewById(R.id.genderAutoCompleteTextView);
        String[] genderOptions = {"Nam", "Nữ", "Khác"};
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, genderOptions);
        genderAutoCompleteTextView.setAdapter(genderAdapter);

        // Handle Date Pickers
        EditText dobEditText = findViewById(R.id.dobEditText);
        EditText issueDateEditText = findViewById(R.id.issueDateEditText);
        EditText expiryDateEditText = findViewById(R.id.expiryDateEditText);

        dobEditText.setOnClickListener(v -> showDatePickerDialog(dobEditText));
        issueDateEditText.setOnClickListener(v -> showDatePickerDialog(issueDateEditText));
        expiryDateEditText.setOnClickListener(v -> showDatePickerDialog(expiryDateEditText));

        // Handle Submit Button
        findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PersonalInfoActivity.this, CaptureIDCardActivity.class));
            }
        });
    }

    private void showDatePickerDialog(final EditText editText) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Format selected date (e.g., DD/MM/YYYY or MM/DD/YYYY)
                    String formattedDate = String.format("%02d/%02d/%d", selectedDay, selectedMonth + 1, selectedYear);
                    editText.setText(formattedDate);
                },
                year, month, day);
        datePickerDialog.show();
    }
} 