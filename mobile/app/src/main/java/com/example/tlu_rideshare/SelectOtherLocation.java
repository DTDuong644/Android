package com.example.tlu_rideshare;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tlu_rideshare.R;

import java.util.ArrayList;
import java.util.List;

public class SelectOtherLocation extends AppCompatActivity {

    private ListView listView;
    private EditText edtSearch;
    private ArrayAdapter<String> adapter;
    private List<String> locationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_other_location);

        listView = findViewById(R.id.list_location);
        edtSearch = findViewById(R.id.edt_search);

        locationList = new ArrayList<>();
        locationList.add("Hà Nội - Bến xe Mỹ Đình");
        locationList.add("Hà Nội - Bến xe Nước Ngầm");
        locationList.add("Thái Nguyên - Bến xe Thành phố");
        locationList.add("Hải Phòng");
        locationList.add("Bắc Ninh");
        locationList.add("Bắc Giang");
        locationList.add("Nam Định");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, locationList);
        listView.setAdapter(adapter);

        // Bắt sự kiện click vào item
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedLocation = adapter.getItem(position);
            Intent resultIntent = new Intent();
            resultIntent.putExtra("selected_location", selectedLocation); // Gửi kết quả về
            setResult(RESULT_OK, resultIntent);
            finish(); // Đóng màn này
        });

        // Optional: Tìm kiếm
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s); // Lọc danh sách
            }
            @Override public void afterTextChanged(Editable s) {}
        });
    }
}
