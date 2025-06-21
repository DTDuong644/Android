package com.example.tlurideshare;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

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

        // Gender dropdown
        AutoCompleteTextView genderAutoCompleteTextView = findViewById(R.id.genderAutoCompleteTextView);
        String[] genderOptions = {"Nam", "Nữ", "Khác"};
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, genderOptions);
        genderAutoCompleteTextView.setAdapter(genderAdapter);

        // Date pickers
        EditText dobEditText = findViewById(R.id.dobEditText);
        EditText issueDateEditText = findViewById(R.id.issueDateEditText);
        EditText expiryDateEditText = findViewById(R.id.expiryDateEditText);

        dobEditText.setOnClickListener(v -> showDatePickerDialog(dobEditText));
        issueDateEditText.setOnClickListener(v -> showDatePickerDialog(issueDateEditText));
        expiryDateEditText.setOnClickListener(v -> showDatePickerDialog(expiryDateEditText));

        // Submit
        findViewById(R.id.submitButton).setOnClickListener(v -> {
            Intent intent = new Intent(PersonalInfoActivity.this, AccountStatusDoneActivity.class);
            startActivity(intent);
            finish(); // Optional
        });
    }

    private void showDatePickerDialog(final EditText editText) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String date = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear);
                    editText.setText(date);
                }, year, month, day);
        dialog.show();
    }
}
